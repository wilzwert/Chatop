package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.JwtToken;
import com.openclassrooms.chatop.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public Optional<JwtToken> extractTokenFromRequest(HttpServletRequest request) throws ExpiredJwtException, MalformedJwtException, SignatureException, SecurityException, IllegalArgumentException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Optional.empty();
        }
        final String token = authHeader.substring(7);

        Jwt<?, ?> parsedToken = Jwts
                .parser().verifyWith(getSignInKey()).build().parse(token);
        Claims claims = (Claims) parsedToken.getPayload();

        JwtToken jwtToken = new JwtToken(claims.getSubject(), claims);
        return Optional.of(jwtToken);
    }

    public String generateToken(User user) {
        return Jwts
                .builder()
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
