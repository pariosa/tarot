package com.mercy.tarot.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mercy.tarot.security.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final UserDetailsService userDetailsService;

        private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SecurityConfig.class);

        public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                        UserDetailsService userDetailsService) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
                this.userDetailsService = userDetailsService;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .csrf(AbstractHttpConfigurer::disable)
                                .headers(headers -> headers
                                                .httpStrictTransportSecurity(hsts -> hsts
                                                                .includeSubDomains(true)
                                                                .preload(true)
                                                                .maxAgeInSeconds(31536000))
                                                .contentSecurityPolicy(csp -> csp
                                                                .policyDirectives("default-src 'self'"))
                                                .frameOptions(frame -> frame
                                                                .sameOrigin()))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(authz -> authz
                                                // Public endpoints
                                                .requestMatchers(
                                                                "/api/auth/**",
                                                                "/api/users",
                                                                "/actuator/health",
                                                                "/api/users/check-email**",
                                                                "/api/public/**",

                                                                "/api/draw",
                                                                "/api/draw/parallel",
                                                                "/api/draw/parallel/weighted",

                                                                "/api/getRandomKeyword")
                                                .permitAll()

                                                // User role endpoints
                                                .requestMatchers(
                                                                "/api/story/location",
                                                                "/api/story/character-trait",
                                                                "/api/getStoryDTO",

                                                                "/api/story/theme",
                                                                "/api/story/keyword",
                                                                "/api/story/moral-value",
                                                                "/api/story/point-of-view",
                                                                "/api/story/style",
                                                                "/api/story/climax-event",
                                                                "/api/spread/weighted/**",
                                                                "/api/spread/parallel/weighted/**",
                                                                "/api/cards/**",
                                                                "/api/readings/**",
                                                                "/api/spread/parallel/**",
                                                                "/api/users/me",
                                                                "/api/users/**")
                                                .hasRole("USER")

                                                // Premium role endpoints
                                                .requestMatchers(
                                                                "/api/story/full-reading/**",
                                                                "/api/story/complete-element/**")
                                                .hasRole("PREMIUM")

                                                // Admin role endpoints
                                                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .exceptionHandling(exception -> exception
                                                .authenticationEntryPoint((request, response, authException) -> {
                                                        log.error("Authentication error: {}",
                                                                        authException.getMessage());
                                                        response.setContentType("application/json");
                                                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                                        response.getWriter().write(
                                                                        "{\"error\":\"Unauthorized\",\"message\":\"Invalid or missing authentication token\"}");
                                                }));

                return http.build();

        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of(
                                "http://localhost:5173",
                                "https://yourproductiondomain.com"));
                configuration.setAllowedMethods(List.of("*"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setExposedHeaders(List.of(
                                "Authorization",
                                "Content-Disposition",
                                "Content-Type",
                                "Access-Control-Allow-Origin",
                                "Access-Control-Allow-Credentials"));
                configuration.setAllowCredentials(true);
                configuration.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

}