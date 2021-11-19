package org.example.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    
    @Value("${jwt.token.secret}")
    private String secret;
    
    @Value("${jwt.token.expired}")
    private long expiredMills;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }
    
    public String createToken(String username, List<Role> roles) {
        
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(roles));
        
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiredMills);
        
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    
    public String getLogin(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
    
    private List<String> getRoleNames(List<Role> userRoles) {
        List<String> result = new ArrayList<>();
        System.out.println(userRoles);
        userRoles.forEach(r -> result.add(r.getName()));
        
        return result;
    }
}