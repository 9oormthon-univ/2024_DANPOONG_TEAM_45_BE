package com.codingland.security.config;

import com.codingland.common.util.HttpResponseUtil;
import com.codingland.security.filter.CustomLogoutHandler;
import com.codingland.security.filter.JwtFilter;
import com.codingland.security.jwt.JwtAccessDeniedHandler;
import com.codingland.security.jwt.JwtAuthenticationEntryPoint;
import com.codingland.security.jwt.JwtUtil;
import com.codingland.security.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtFilter jwtFilter;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors
                        .configurationSource(CorsConfig.apiConfigurationSource()));

        // csrf disable
        http
                .csrf(AbstractHttpConfigurer::disable);

        // form 로그인 방식 disable
        http
                .formLogin(AbstractHttpConfigurer::disable);

        // http basic 인증 방식 disable
        http
                .httpBasic(AbstractHttpConfigurer::disable);

        // 경로별 인가 작업
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api-docs").permitAll()
                        .requestMatchers("/v1/users/login/**").permitAll()
                        .requestMatchers("/v1/api/**").authenticated()
                        .anyRequest().permitAll()
                );

        // 세션 사용 안함
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // JwtExceptionFilter 사용
        http
                .addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                );

        // Logout Filter
        http
                .logout(logout -> logout
                        .logoutUrl("/v1/users/logout")
                        .addLogoutHandler(new CustomLogoutHandler(redisUtil, jwtUtil))
                        .logoutSuccessHandler((request, response, authentication)
                                -> HttpResponseUtil.setSuccessResponse(
                                response,
                                HttpStatus.OK,
                                "로그아웃 성공"
                        ))
                );

        return http.build();
    }

}
