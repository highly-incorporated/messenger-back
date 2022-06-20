package com.corporate.jwt;

import com.corporate.model.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JWTProvider {

    public String generateAccessToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusMinutes(15).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS512, "twowasik")
                .claim("id", user.getId())
                .compact();
    }

    public String generateRefreshToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        Instant refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(refreshExpiration)
                .signWith(SignatureAlgorithm.HS512, "kisawowt")
                .compact();
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, "twowasik");
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, "kisawowt");
    }

    private boolean validateToken(String token, String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            System.out.println("Token expired " + expEx);
        } catch (UnsupportedJwtException unsEx) {
            System.out.println("Unsupported jwt " + unsEx);
        } catch (MalformedJwtException mjEx) {
            System.out.println("Malformed jwt " + mjEx);
        } catch (SignatureException sEx) {
            System.out.println("Invalid signature " + sEx);
        } catch (Exception e) {
            System.out.println("invalid token " + e);
        }
        return false;
    }

    public Claims getAccessClaims(String token) {
        return getClaims(token, "twowasik");
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, "kisawowt");
    }

    private Claims getClaims(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}