package com.medilabo.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


/**
 * Configuration class for handling security in the application using Spring WebFlux Security.
 */
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    /**
     * Configures security filters for different endpoints in the application.
     *
     * @param http The ServerHttpSecurity object to customize security settings.
     * @return The configured SecurityWebFilterChain.
     */
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .httpBasic(Customizer.withDefaults()).csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/patients/**").authenticated()
                        .pathMatchers("/auth/**").authenticated()
                        .anyExchange().permitAll());

        return http.build();
    }


    /**
     * Provides a simple in-memory user details service with a default user.
     *
     * @param passwordEncoder The password encoder to encode user passwords.
     * @return The configured MapReactiveUserDetailsService.
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    /**
     * Provides an instance of BCryptPasswordEncoder for encoding passwords.
     *
     * @return The configured BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
