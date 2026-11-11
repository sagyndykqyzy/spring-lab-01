
package kz.iitu.springlab;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // Public pages
                        .requestMatchers(
                                HttpMethod.GET,
                                "/", "/login", "/error", "/css/**"
                        ).permitAll()

                        // Only ADMIN can modify books
                        .requestMatchers(
                                HttpMethod.POST,
                                "/books", "/books/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/books", "/books/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/books", "/books/**"
                        ).hasRole("ADMIN")

                        // Admin dashboard
                        .requestMatchers(
                                "/admin", "/admin/**"
                        ).hasRole("ADMIN")

                        // USER and ADMIN can view books
                        .requestMatchers(
                                HttpMethod.GET,
                                "/books", "/books/**"
                        ).hasAnyRole("USER", "ADMIN")

                        .anyRequest().denyAll()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/books", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

