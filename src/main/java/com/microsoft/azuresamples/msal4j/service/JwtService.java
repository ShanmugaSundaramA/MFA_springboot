package com.microsoft.azuresamples.msal4j.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {

     @Value("${app.secret-key}")
     private String secretKey;

     @Value("${app.expiration-ms}")
     private int expirationMs;

     public String extractUsername(String token) {
          return extractClaim(token, Claims::getSubject);
     }

     public <R> R extractClaim(String token, Function<Claims, R> claimsResolver) {
          final Claims claims = extractAllClaims(token);
          return claimsResolver.apply(claims);
     }

     public String generateToken(String email) {
          return generateToken(new HashMap<>(), email);
     }

     public String generateToken(Map<String, Object> extractClaims, String email) {
          return Jwts.builder()
                    .addClaims(extractClaims)
                    .setSubject(email)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
     }

     public boolean isTokenValid(String token, UserDetails userDetails) {
          final String username = extractUsername(token);
          return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);

     }

     public boolean isTokenExpired(String token) {
          return extractExpiration(token).before(new Date());
     }

     public Date extractExpiration(String token) {
          return extractClaim(token, Claims::getExpiration);
     }

     public Claims extractAllClaims(String token) {
          return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
     }

     public Key getSignInKey() {
          byte[] keyBytes = Decoders.BASE64.decode(secretKey);
          return Keys.hmacShaKeyFor(keyBytes);
     }

     public String validateJwtToken(String authToken) {
          try {
               Jwts.parserBuilder()
                         .setSigningKey(getSignInKey())
                         .build()
                         .parseClaimsJws(authToken);
               return "valid";
          } catch (MalformedJwtException e) {
               return "invalid";
          } catch (ExpiredJwtException e) {
               deleteJwtTokenFromLocalStorage();
               return "expired";
          } catch (UnsupportedJwtException e) {
               return "unsupported";
          } catch (IllegalArgumentException e) {
               return "empty";
          } catch (Exception e) {
               return e.getMessage();
          }
     }

     public void deleteJwtTokenFromLocalStorage() {
          try {
               java.util.prefs.Preferences.userRoot().remove("jwtToken");
          } catch (Exception e) {
               log.error(e.getMessage());
          }
     }
}
