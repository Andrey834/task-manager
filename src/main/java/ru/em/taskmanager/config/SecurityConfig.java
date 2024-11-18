package ru.em.taskmanager.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.em.taskmanager.handlers.AccessDeniedHandlerJwt;
import ru.em.taskmanager.handlers.AuthenticationEntryPointJwt;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final AccessDeniedHandlerJwt accessDeniedHandlerJwt;
    private final AuthenticationEntryPointJwt authenticationEntryPointJwt;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                //.requestMatchers("/admin/**").hasRole(ERole.ADMIN.name())
                                .requestMatchers("/auth/signin").permitAll()
                                .requestMatchers("/auth/signup").permitAll()
                                .requestMatchers("/auth/refresh").permitAll()
                                .requestMatchers("/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/v3/**").permitAll()
                                .requestMatchers("/auth/logout").authenticated()
                                .anyRequest().authenticated())
                .exceptionHandling(except ->
                        except
                                .accessDeniedHandler(accessDeniedHandlerJwt))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
