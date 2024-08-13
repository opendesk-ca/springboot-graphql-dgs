package com.accounts.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
@Slf4j
public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    public JwtAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @PostConstruct
    public void init() {
        setServerAuthenticationConverter(new JwtAuthenticationConverter(jwtSecret));
    }

    private class JwtAuthenticationConverter implements ServerAuthenticationConverter {

        private final String jwtSecret;

        public JwtAuthenticationConverter(String jwtSecret) {
            this.jwtSecret = jwtSecret;
        }

        @Override
        public Mono<Authentication> convert(ServerWebExchange exchange) {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                log.info("Received JWT: {}", token);

                Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

                try {
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

                    String username = claims.getSubject();
                    log.info("Authenticated user: {}", username);

                    return Mono.just(new UsernamePasswordAuthenticationToken(username, null, null));
                } catch (JwtException e) {
                    log.error("Invalid JWT: " + e.getMessage());
                    return Mono.empty(); // Invalid JWT
                }
            }
            log.info("No JWT provided in Authorization header");
            return Mono.empty(); // No JWT provided
        }
    }
}