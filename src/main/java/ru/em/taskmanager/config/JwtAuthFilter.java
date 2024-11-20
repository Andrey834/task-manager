package ru.em.taskmanager.config;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.em.taskmanager.service.JwtService;

import java.io.IOException;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
@Hidden
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        List<String> allowList = List.of(
                contextPath + "/auth/signin",
                contextPath + "/auth/signup",
                contextPath + "/auth/refresh");

        if (allowList.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getHeader("Authorization") != null) {
            String prefixToken = "Bearer ";
            final String accessToken = request.getHeader("Authorization").substring(prefixToken.length());
            if (isNotEmpty(accessToken)) {
                final String email = jwtService.extractEmail(accessToken);
                final boolean loggedIn = Boolean.TRUE.equals(redisTemplate.hasKey(email));

                if (isNotEmpty(email)
                    && SecurityContextHolder.getContext().getAuthentication() == null
                    && jwtService.isTokenValid(accessToken)
                    && loggedIn
                ) {
                    final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    SecurityContext context = SecurityContextHolder.createEmptyContext();

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            jwtService.getRoles(accessToken).stream()
                                    .map(Object::toString)
                                    .map(SimpleGrantedAuthority::new)
                                    .toList()
                    );
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(token);
                    SecurityContextHolder.setContext(context);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
