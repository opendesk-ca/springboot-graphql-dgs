package com.accounts.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.server.SecurityWebFilterChain;

import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(exchange -> exchange
                        .pathMatchers("/graphql").authenticated()
                        .anyExchange().permitAll()
                )
                .httpBasic(withDefaults())  // Enable HTTP Basic authentication
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                )
                .addFilterAt(csrfTokenLoggingFilter(), SecurityWebFiltersOrder.CSRF);

        return http.build();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    /* @Bean
     public WebFilter csrfTokenLoggingFilter() {
         return (exchange, chain) -> {
             Mono<CsrfToken> csrfToken = exchange.getAttributeOrDefault(CsrfToken.class.getName(), Mono.empty());
             return csrfToken.doOnNext(token -> {
                 System.out.println("CSRF Token: " + token.getToken()); // Log the CSRF token
             }).then(chain.filter(exchange));
         };
     }*/

    @Bean
    public WebFilter csrfTokenLoggingFilter() {
        return new WebFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

                // Step 1: Retrieve the CSRF token from the exchange attributes.
                Mono<CsrfToken> csrfTokenMono = exchange.getAttributeOrDefault(CsrfToken.class.getName(), Mono.empty());

                // Step 2: Log the CSRF token if it's present.
                Mono<Void> loggingMono = csrfTokenMono.doOnNext(csrfToken -> {
                    if (csrfToken != null) {
                        String tokenValue = csrfToken.getToken();
                        System.out.println("CSRF Token: " + tokenValue); // Log the CSRF token
                    }
                }).then();

                // Step 3: Continue the filter chain after logging.
                return loggingMono.then(chain.filter(exchange));
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
