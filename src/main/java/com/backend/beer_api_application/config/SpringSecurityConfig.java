package com.backend.beer_api_application.config;

import com.backend.beer_api_application.filter.JwtRequestFilter;
import com.backend.beer_api_application.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth

                        // BeerController endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/beers").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/beers/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/beers").hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/beers/{id}").hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/beers/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/beers/{id}/in-stock").hasAnyRole("ADMIN", "EDITOR")

                        // ImageUploadController endpoints
                        .requestMatchers(HttpMethod.POST, "/api/v1/images/upload/{beerId}").hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/images/{imageId}").hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/images/{imageId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/images/{imageId}/download").permitAll()

                        // TasteController endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/tastes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/tastes/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/tastes").hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/tastes/{id}").hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tastes/{id}").hasRole("ADMIN")

                        // CategoryController endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories").hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/categories/{id}").hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/{id}").hasAnyRole("ADMIN", "EDITOR")

                        // CustomerController endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/customers/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/{id}").hasAnyRole("ADMIN", "USER")

                        // OrderController endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders/{orderId}/order-lines").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/{orderId}/order-lines/{orderLineId}").hasAnyRole("ADMIN", "USER")

                        // OrderStatus endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/{id}/status").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/orders/{id}/status").hasAnyRole("ADMIN", "USER")

                        // OrderLineController endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/order-lines").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/order-lines/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/order-lines").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/order-lines/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/order-lines/{id}").hasAnyRole("ADMIN", "USER")

                        // User endpoints individual
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/me").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/me").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/me").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/me/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/me/authorities").hasRole("ADMIN")

                        // All users endpoints
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/{username}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/{username}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/{username}").hasRole("ADMIN")

                        // Public en authentication endpoints
                        .requestMatchers("/api/v1/authenticated").authenticated()
                        .requestMatchers("/api/v1/authenticate").permitAll()

                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Voeg JWT filter toe
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
