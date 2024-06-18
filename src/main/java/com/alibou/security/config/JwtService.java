package com.alibou.security.config;

import com.alibou.security.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private final Key key = Keys.hmacShaKeyFor("YourSecretKeyMustBeAtLeast256BitsLong".getBytes());

  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .claim("userId", ((User) userDetails).getId())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
  }

  public String generateRefreshToken(UserDetails userDetails) {
    return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days expiration
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
  }

  public Claims parseToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  public String extractUsername(String token) {
    return parseToken(token).getSubject();
  }

  public Integer extractUserId(String token) {
    return parseToken(token).get("userId", Integer.class);
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return parseToken(token).getExpiration().before(new Date());
  }
}
