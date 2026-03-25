package com.futureflowhome.todo_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Use path-pattern matchers for API paths: with Spring MVC present, plain
     * {@code requestMatchers("/api/**")} resolves to {@code MvcRequestMatcher}, which only
     * matches if a controller mapping exists for that HTTP method. {@code GET /api/tasks}
     * has no handler (only {@code POST} is mapped), so the rule would not apply and
     * {@code anyRequest().denyAll()} would yield 403 even with a valid JWT.
     * 
     * More specific API paths listed before {@code /api/**} can use {@code permitAll()}
     * (e.g. demo {@code GET /api/security-demo/public}).
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationEntryPoint authenticationEntryPoint,
            AccessDeniedHandler accessDeniedHandler)
            throws Exception {
        http
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathPatternRequestMatcher.pathPattern("/actuator/**")).permitAll()
                        .requestMatchers(PathPatternRequestMatcher.pathPattern("/api/security-demo/public"))
                                .permitAll()
                        .requestMatchers(PathPatternRequestMatcher.pathPattern("/api/**")).authenticated()
                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(csrf -> csrf.disable())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));
        return http.build();
    }
}
