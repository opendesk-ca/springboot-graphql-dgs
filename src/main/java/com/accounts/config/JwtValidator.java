package com.accounts.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JwtValidator {

    public static void main(String[] args) {
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.zU7f5_YmAxvUJ2VbTdEx-lfHG7MKA6TTbaarp7ucnyc";

        String secret = "My GraphQL Secret Key should be at least - The specified key byte array is 168 bits which is not secure enough for any JWT HMAC-SHA algorithm";
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        try {
            // Parse and validate the signed JWT
            Claims claims = Jwts.parserBuilder()
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
