package com.content.repository.api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;

    private static final long EXPIRATION_TIME = 864_000_000;

    public String generateToken() {
        return Jwts.builder()
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).build().parse(token);
            return true;
        } catch (Exception e) {
        }
        return false;
    }
}
