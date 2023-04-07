package com.example.epolsoftbackend.another;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
@Component
public class CustomInterceptor implements HandlerInterceptor {

    @Autowired
    private JsonWebToken jsonWebToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception{
        String jwtAuthToken = null;
        Jws<Claims> jws;
        String secret = null;

        if(request.getHeader("Authorization ") != null) {
            try {
                jwtAuthToken = request.getHeader("Authorization ").replace("Bearer ", "");
                jws = jsonWebToken.validateToken(jwtAuthToken);
            } catch (Exception e) { return false; }

            final Matcher m = Pattern
                    .compile("(/api/authors/get)|(/api/topic/get/all)|(/api/topic/create)|(/api/topic/delete)|" +
                            "(/api/topic/get/available)|(/api/book)|(/api/file/upload)|(/api/file/delete)|(/api/book/delete)|(/api/book/update)")
                    .matcher(request.getServletPath());
            if (m.find())
                switch (m.group()) {
                    case "/api/authors/get":
                    case "/api/topic/get/all":
                    case "/api/topic/create":
                    case "/api/topic/delete":
                        return jws.getBody().get("Admin", Boolean.class);
                    case "/api/topic/get/available":
                    case "/api/book":
                    case "/api/file/upload":
                        return jws.getBody().get("User", Boolean.class);
                    case "/api/file/delete":
                    case "/api/book/delete":
                    case "/api/book/update":
                        request.setAttribute("idUser",jws.getBody().getId());
                        return jws.getBody().get("User", Boolean.class);
                    default: return false;
                }
            else return false;
        }
        else return false;
    }
}
