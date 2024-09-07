package com.ecom.ecommerce.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Protect endpoint /api/orders
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/orders/**").authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {

                }));

        // Add CORS filters (you can enable this if needed)
        http.cors(cors -> cors.disable());

        // Add content negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class,
                new HeaderContentNegotiationStrategy());

        // Make 401 responses more user-friendly
        Okta.configureResourceServer401ResponseBody(http);

        // Disable CSRF as we are not using cookies for session tracking
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
