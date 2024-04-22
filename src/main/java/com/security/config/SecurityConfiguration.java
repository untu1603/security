package com.security.config;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@EnableWebSecurity
public class SecurityConfiguration {
    private static final String[] WHITE_LIST_URL = {
            "/swagger-ui.html"};
}
