package com.accounts.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JwtValidator {

    public static void main(String[] args) {
        String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJncmFwaHFsQGV4YW1wbGUuY29tIiwiZ3JhcGhxbEBleGFtcGxlLmNvbSI6ImdyYXBocWxAZXhhbXBsZS5jb20iLCJpYXQiOjE3MjQxNzQwNzl9.9nXUiYEjG-eeMPCNDs4HoDm_e2US1JsFOa1Fkk7ziqMYS0r93dcG6gQjw0Q5yIBvisqGmMQSqWH-DE4637B5Og";

        String secret = "Oh! My GraphQL Secret Key, You should be at least - 168 bites long inorder for to secure JWT HMAC-SHA algorithm. You make sure you are long enough not be vulnerable.";
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        try {
            // Parse and validate the signed JWT
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            System.out.println("JWT is valid");
            System.out.println("JWT Claims: " + claims);
        } catch (Exception e) {
            System.out.println("Invalid JWT: " + e.getMessage());
        }
    }
}
