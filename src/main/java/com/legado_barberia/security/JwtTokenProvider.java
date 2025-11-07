package com.legado_barberia.security;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component;
import java.security.Key; import java.util.Date; import java.util.Map;

@Component
public class JwtTokenProvider {
  private final Key key; private final long expirationMs;
  public JwtTokenProvider(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long exp){
    this.key = Keys.hmacShaKeyFor(secret.getBytes()); this.expirationMs = exp;
  }
  public String generate(String subject, Map<String,Object> claims){
    Date now = new Date(), exp = new Date(now.getTime()+expirationMs);
    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now).setExpiration(exp)
      .signWith(key, SignatureAlgorithm.HS256).compact();
  }
  public String getSubject(String token){
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
  }
}
