package com.example.epolsoftbackend.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JsonWebTokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private JsonWebTokenProvider jsonWebTokenProvider;
    public JsonWebTokenConfigurer(JsonWebTokenProvider jsonWebTokenProvider) {
        this.jsonWebTokenProvider = jsonWebTokenProvider;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JsonWebTokenFilter jsonWebTokenFilter = new JsonWebTokenFilter(jsonWebTokenProvider);
        builder.addFilterBefore(jsonWebTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
