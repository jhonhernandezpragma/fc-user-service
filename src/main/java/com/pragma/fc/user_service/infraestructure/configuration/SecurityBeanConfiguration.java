package com.pragma.fc.user_service.infraestructure.configuration;

import com.pragma.fc.user_service.infraestructure.exceptionHandler.ErrorUtils;
import com.pragma.fc.user_service.infraestructure.input.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityBeanConfiguration {
    @Value("${app.api.prefix}")
    private String apiPrefix;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        final String[] WHITE_LIST_URL = {
                apiPrefix + "/auth/login",
                "/swagger-ui/**",
                "/v3/api-docs/**",
        };

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) ->
                                ErrorUtils.writeError(response, HttpStatus.UNAUTHORIZED, "Unauthorized", request.getRequestURI())
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                ErrorUtils.writeError(response, HttpStatus.FORBIDDEN, "Forbidden", request.getRequestURI())
                        )
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
