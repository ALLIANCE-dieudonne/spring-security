package com.alliance.springsecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

  private static final String SECRETE_KEY = "7oK/e/YYB03MQwHDlb8Q66eUMdb6JRSqC8vH7HdOOWHM8VOCu/kp8GucigUBxM5zHio";

  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  //extracting claims from the provided token
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  //generating a token without extra claims
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  //validating a token
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUserName(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  //seeing if token is expired
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  //seeing the expiration time of token
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);

  }

  //generating a token from extra claims
  public String generateToken(
    Map<String, Object> extraClaims,
    UserDetails userDetails
  ) {
    return Jwts.builder()
      .setClaims(extraClaims)
      .setSubject(userDetails.getUsername())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
      .signWith(getSignInKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  //extracting the claims from token
  private Claims extractAllClaims(String token) {
    return Jwts
      .parserBuilder()
      .setSigningKey(getSignInKey())
      .build()
      .parseClaimsJws(token)
      .getBody();

  }

  //decoding the key from the token and generating a sign in key
  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRETE_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }


}
