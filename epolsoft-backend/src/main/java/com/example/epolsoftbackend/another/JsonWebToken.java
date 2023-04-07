package com.example.epolsoftbackend.another;

import com.example.epolsoftbackend.user.User;
import com.example.epolsoftbackend.user_role.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Component
public class JsonWebToken {

    private final String secret;

    public JsonWebToken(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String generateToken(User user) {

        boolean haveUserRole = false, haveAdminRole = false;
        for (UserRole userRole: user.getRoles()) {
            switch (userRole.getRole().getName()){
                case "USER":
                    haveUserRole = true;
                case "ADMIN":
                    haveAdminRole = true;
            }
        }

        Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        String jwtToken = Jwts.builder()
                .claim("mail", user.getMail())
                .claim("Admin", haveAdminRole)
                .claim("User", haveUserRole)
                .setSubject(user.getName())
                .setId(user.getId().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(5l, ChronoUnit.MINUTES)))
                .signWith(secretKey)
                .compact();
        return jwtToken;
    }

    public Jws<Claims> parseToken(String jwtString) {

        Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtString);//signature exception expiredjwtexception if time token is vse
//        jws.getBody().get("mail", String.class);
        return jws;
    }


}
