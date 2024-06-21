package org.auth.api.application.configs.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.auth.api.application.services.JwtService;
import org.auth.api.application.services.UserService;
import org.auth.api.infrastructure.gateway.UserGatewayImpl;
import org.auth.api.infrastructure.helper.HelperMapper;
import org.auth.api.infrastructure.model.UserModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserService service;
    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return (request.getRequestURI().startsWith("/api/v1/auth"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String requestToken = request.getHeader("Authorization");

        String token = null;
        String username = null;

        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);
            username = jwtService.extractUser(token);
        } else {
            throw new RuntimeException("O token não começa com [Bearer].");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var user = service.loadUserByUsername(username);

            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null,
                    user.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
