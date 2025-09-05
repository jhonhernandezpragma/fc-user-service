package com.pragma.fc.user_service.infraestructure.input.security.filter;

import com.pragma.fc.user_service.domain.spi.ITokenServicePort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final ITokenServicePort tokenServicePort;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        if (token == null || token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean isValid = tokenServicePort.isAccessTokenValid(token);
        if (!isValid) {
            filterChain.doFilter(request, response);
            return;
        }

        String userEmail = tokenServicePort.extractSubject(token);
        String role = tokenServicePort.extractRole(token);
        if (userEmail == null || role == null) {
            filterChain.doFilter(request, response);
            return;
        }

        GrantedAuthority authorities = new SimpleGrantedAuthority("ROLE_" + role);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userEmail,
                null,
                List.of(authorities)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
