package com.codingland.security.jwt;

import com.codingland.common.exception.security.SecurityCustomException;
import com.codingland.common.exception.user.UserErrorCode;
import com.codingland.common.exception.user.UserException;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.repository.UserRepository;
import com.codingland.security.jwt.dto.UserInfoDTO;
import com.codingland.security.oauth.dto.response.LoginResponse;
import com.codingland.security.redis.util.RedisUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.codingland.common.exception.security.SecurityErrorCode.INVALID_TOKEN;
import static com.codingland.common.exception.security.SecurityErrorCode.TOKEN_EXPIRED;

@Component
@Slf4j
public class JwtUtil {

    private static final String USERNAME = "username";
    private static final String AUTHORITIES_CLAIM_NAME = "auth";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORITIES = "role";

    private final SecretKey secretKey;
    private final Long accessExpMs;
    private final Long refreshExpMs;
    private final RedisUtil redisUtil;

    private final UserRepository userRepository;

    public JwtUtil(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.token.access-expiration-time}") Long access,
            @Value("${security.jwt.token.refresh-expiration-time}") Long refresh,
            RedisUtil redis,
            UserRepository userRepository) {

        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        accessExpMs = access;
        refreshExpMs = refresh;
        redisUtil = redis;
        this.userRepository = userRepository;
    }

    public String createJwtAccessToken(String email, String subId) {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plusMillis(accessExpMs);

        return Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setSubject(subId)
                .claim("email", email)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();
    }

    public String createJwtRefreshToken(String email, String subId) {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plusMillis(refreshExpMs);

        String refreshToken = Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setSubject(subId)
                .claim("email", email)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();

        redisUtil.saveAsValue(
                email + "_refresh_token",
                refreshToken,
                refreshExpMs,
                TimeUnit.MILLISECONDS
        );

        return refreshToken;
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    public Authentication resolveToken(String token) {

        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<SimpleGrantedAuthority> authorities = Stream.of(
                        String.valueOf(claims.get(AUTHORITIES)).split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        final User user = findUser(getEmail(token));
        UserInfoDTO infoDTO = new UserInfoDTO(user.getName(), user.getEmail(), user.getPicture());

        return new UsernamePasswordAuthenticationToken(infoDTO, token, authorities);
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
    }

    public boolean validateToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            token = token.substring(BEARER_PREFIX.length());
        }

        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("[*] Token has expired: {}", e.getMessage());
            throw new SecurityCustomException(TOKEN_EXPIRED);
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException | UnsupportedJwtException e) {
            log.warn("[*] Invalid token: {}", e.getMessage());
            throw new SecurityCustomException(INVALID_TOKEN);
        }
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.warn("[*] No token in req");
            return null;
        }

        log.info("[*] Token exists");
        return authorization.split(" ")[1];
    }

    public LoginResponse reissueToken(String refreshToken) {
        try {
            validateRefreshToken(refreshToken);
            log.info("[*] Valid RefreshToken");

            // 삭제 로직
            String email = getEmail(refreshToken);
            redisUtil.delete(email + "_refresh_token");

            String subId = getSubjectFromToken(refreshToken);

            return new LoginResponse(
                    createJwtAccessToken(email, subId),
                    createJwtRefreshToken(email, subId)
            );
        } catch (IllegalArgumentException iae) {
            throw new SecurityCustomException(INVALID_TOKEN, iae);
        } catch (ExpiredJwtException eje) {
            throw new SecurityCustomException(TOKEN_EXPIRED, eje);
        }
    }

    public void deleteToken(String refreshToken) {

        // 삭제 로직
        String email = getEmail(refreshToken);
        redisUtil.delete(email + "_refresh_token");
    }

    public void validateRefreshToken(String refreshToken) {
        // refreshToken 유효성 검증
        String email = getEmail(refreshToken);

        // redis에 refreshToken 있는지 검증
        if (!redisUtil.hasKey(email + "_refresh_token")) {
            log.warn("[*] case : Invalid refreshToken");
            throw new SecurityCustomException(INVALID_TOKEN);
        }
    }

    public String getSubjectFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getUsername(String token) {
        return getClaims(token).get(USERNAME, String.class);
    }

    public Long getId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    public String getEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    public String getAuthority(String token) {
        return getClaims(token).get(AUTHORITIES_CLAIM_NAME, String.class);
    }

    public Boolean isExpired(String token) {
        // 여기서 토큰 형식 이상한 것도 걸러짐
        return getClaims(token).getExpiration().before(Date.from(Instant.now()));
    }

    public Long getExpTime(String token) {
        return getClaims(token).getExpiration().getTime();
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new SecurityCustomException(INVALID_TOKEN, e);
        }
    }
}
