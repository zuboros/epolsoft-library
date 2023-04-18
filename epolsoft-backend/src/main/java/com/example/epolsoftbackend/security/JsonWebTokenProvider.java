package com.example.epolsoftbackend.security;

import com.example.epolsoftbackend.exception.UnauthorizedException;
import com.example.epolsoftbackend.user.CustomUserDetailsService;
import com.example.epolsoftbackend.user.User;
import com.example.epolsoftbackend.user.UserRepository;
import com.example.epolsoftbackend.user_role.UserRole;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Component
public class
JsonWebTokenProvider {

    private final String secret;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;

    private JsonWebTokenProvider(@Value("${jwt.secret}") String secret,
                                 CustomUserDetailsService customUserDetailsService, UserRepository userRepository) {
        this.secret = secret;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
    }

    public String generateToken(User user) {

        boolean haveUserRole = false, haveAdminRole = false, haveModeratorRole = false;
        for (UserRole userRole: user.getRoles()) {
            switch (userRole.getRole().getName()){
                case "USER":
                    haveUserRole = true;
                case "ADMIN":
                    haveAdminRole = true;
                case "MODERATOR":
                    haveModeratorRole = true;
            }
        }

        Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .claim("Mail", user.getMail())
                .claim("ADMIN", haveAdminRole)
                .claim("USER", haveUserRole)
                .claim("MODERATOR", haveModeratorRole)
                .setSubject(user.getName())
                .setId(user.getId().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(5l, ChronoUnit.HOURS)))
                .signWith(secretKey)
                .compact();
    }

    public Jws<Claims> parseToken(String jwtString) {
        try {
            Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwtString);
            return jws;
        }   catch (Exception e) { return null; }
    }

    public boolean validateToken(String token) {
        try {
            Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();

            User user = userRepository.findById(Long.parseLong(Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getId())).get();
            if (userRepository.isPasswordExpired(user.getPasswordUpdatedAt())) {
                throw new UnauthorizedException("EXPIRED PASSWORD");
            }

            return true;
        } catch (Exception e) {return false;}
    }

    public String resolveToken(HttpServletRequest servletRequest){
        String bearerToken=servletRequest.getHeader("Authorization");
        if (bearerToken!=null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(7,bearerToken.length());
        }
        return null;
    }
    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.customUserDetailsService.loadUserByMail(parseToken(token).getBody().get("Mail", String.class));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
