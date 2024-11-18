package ru.em.taskmanager.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.em.taskmanager.config.RsaProperty;
import ru.em.taskmanager.dto.auth.Tokens;
import ru.em.taskmanager.service.JwtService;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${app.jwt.lifecycle.access}")
    private Duration ACCESS_TIME;
    @Value("${app.jwt.lifecycle.refresh}")
    private Duration REFRESH_TIME;
    @Value("${spring.application.name}")
    private String APP_ISSUER;
    private final RsaProperty keys;

    @Override
    public Tokens generateTokens(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("roles", roles);

        final String accessToken = Jwts.builder()
                .subject(userDetails.getUsername())
                .issuer(APP_ISSUER)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TIME.toMillis()))
                .claims(claims)
                .signWith(keys.privateKey())
                .compact();

        final String refreshToken = Jwts.builder()
                .subject(userDetails.getUsername())
                .issuer(APP_ISSUER)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TIME.toMillis()))
                .signWith(keys.privateKey())
                .compact();

        return Tokens.builder()
                .access(accessToken)
                .refresh(refreshToken)
                .build();
    }

    @Override
    public String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token) {
        return (!isTokenExpired(token) && appIssuer(token));
    }

    @Override
    public List<?> getRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration)
                .before(new Date(System.currentTimeMillis()));
    }

    private boolean appIssuer(String token) {
        final String issuer = extractClaims(token, Claims::getIssuer);
        return APP_ISSUER.equals(issuer);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) throws IllegalArgumentException {
            return Jwts.parser()
                    .verifyWith(keys.publicKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    }
}
