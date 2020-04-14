package com.zanella.cursomc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public boolean validToken(String token) {
        Claims claims = getClaims(token);
        if(claims != null) {
            String username = claims.getSubject();
            Date expirationToken = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if (username != null && expirationToken != null && now.before(expirationToken)) {
                return true;
            }
        }
        return false;
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if(claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        }catch (Exception e) {
            return null;
        }
    }

}
