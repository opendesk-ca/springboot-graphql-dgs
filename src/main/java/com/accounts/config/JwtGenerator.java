package com.accounts.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtGenerator {

    public static void main(String[] args) {
        String secret = "My GraphQL Secret Key should be at least - The specified key byte array is 168 bits which is not secure enough for any JWT HMAC-SHA algorithm";
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;

        // Example JWT generation
        String jwt = Jwts.builder()
                .setSubject("1234567890")
                .claim("name", "John Doe")
                .setIssuedAt(new Date())
                .signWith(key, algorithm)
                .compact();

        System.out.println("key: " + key + "\nGenerated JWT: " + jwt);
    }
}
