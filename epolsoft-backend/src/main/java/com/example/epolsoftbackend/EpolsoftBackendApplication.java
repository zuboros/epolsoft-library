package com.example.epolsoftbackend;

import com.example.epolsoftbackend.configuration.MyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ MyConfig.class })
public class EpolsoftBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpolsoftBackendApplication.class, args);
    }

}
