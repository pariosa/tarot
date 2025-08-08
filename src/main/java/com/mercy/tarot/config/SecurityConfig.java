package com.mercy.tarot.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mercy.tarot.security.FirebaseAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final FirebaseAuthenticationFilter firebaseAuthenticationFilter;

    public SecurityConfig(FirebaseAuthenticationFilter firebaseAuthenticationFilter) {
        this.firebaseAuthenticationFilter = firebaseAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        // Public endpoints - General
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/actuator/health").permitAll()

                        // Public endpoints - Card related (Free tier)
                        .requestMatchers("/api/cards/daily").permitAll()
                        .requestMatchers("/api/spread/weighted/**").permitAll()
                        .requestMatchers("/api/spread/parallel/weighted/**").permitAll()
                        .requestMatchers("/api/draw/parallel").permitAll()
                        .requestMatchers("/api/draw/parallel/weighted").permitAll()

                        // Public endpoints - Story elements (Free tier "taste" endpoints)
                        .requestMatchers("/api/story/location").permitAll()
                        .requestMatchers("/api/story/character-trait").permitAll()
                        .requestMatchers("/api/story/theme").permitAll()
                        .requestMatchers("/api/story/keyword").permitAll()
                        .requestMatchers("/api/story/moral-value").permitAll()
                        .requestMatchers("/api/story/point-of-view").permitAll()
                        .requestMatchers("/api/story/style").permitAll()
                        .requestMatchers("/api/story/climax-event").permitAll()

                        // Public endpoints - Legacy story endpoints
                        .requestMatchers("/api/getStoryDTO").permitAll()
                        .requestMatchers("/api/getRandomKeyword").permitAll()

                        // Private endpoints - Cards (Authenticated users)
                        .requestMatchers("/api/cards/**").hasRole("USER")
                        .requestMatchers("/api/readings/**").hasRole("USER")
                        .requestMatchers("/api/spread/parallel/**").hasRole("USER") // Non-weighted parallel spreads
                                                                                    // require auth

                        // Private endpoints - Full story features (Paid tier)
                        .requestMatchers("/api/story/full-reading/**").hasRole("PREMIUM")
                        .requestMatchers("/api/story/complete-element/**").hasRole("PREMIUM")

                        // Admin endpoints
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated())
                .addFilterBefore(firebaseAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}