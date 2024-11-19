package com.codingland.security.filter;

import com.codingland.common.common.ApplicationResponse;
import com.codingland.common.exception.security.SecurityCustomException;
import com.codingland.security.jwt.JwtAuthenticationEntryPoint;
import com.codingland.security.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String jwt = jwtUtil.resolveToken(request);
            if (jwt != null) {
                jwtUtil.validateToken(jwt); // 여기서 예외 발생 가능
                setAuthentication(jwt);
            }
            chain.doFilter(request, response);
        } catch (SecurityCustomException e) {
            log.error("Custom security exception: {}", e.getMessage());
            response.setStatus(e.getErrorCode().getHttpStatus().value());
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(
                            ApplicationResponse.ok(e.getErrorCode().getCode(), e.getErrorCode().getMessage())
                    )
            );
        } catch (AuthenticationException e) {
            jwtAuthenticationEntryPoint.commence(request, response, e);
        }
    }

    private void setAuthentication(String accessToken) {
        Authentication authentication = jwtUtil.resolveToken(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
