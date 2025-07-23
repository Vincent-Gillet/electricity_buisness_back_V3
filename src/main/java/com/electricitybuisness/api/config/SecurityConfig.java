package com.electricitybuisness.api.config;

import com.electricitybuisness.api.repository.UtilisateurRepository;
import com.electricitybuisness.api.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailService userDetailsService;

    private final JwtService jwtService;

    private final JwtAuthFilter jwtAuthFilter;

    private final UtilisateurRepository utilisateurRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authz) -> authz

                        .requestMatchers(
                                "/",
                                "/inscription",
                                "/connexion",
                                "/css/**",
                                "/api/auth/**",
                                "/api/auth/login",
                                "/api/utilisateurs/register",
                                "/api/bornes/**",
                                "/api/auth/login-form",

                                "/api/options/**",
                                "/api/lieux/**",
                                "/api/reservations/**",
                                "/api/vehicules/**",
                                "/api/medias/**",
                                "/api/utilisateurs/**",
                                "/api/adresses/**"

                                ).permitAll()

                        .requestMatchers(
                                "/api/admin/**",
                                "/api/reparateurs/**",
                                "/api/utilisateurs/banni",
                                "/api/utilisateurs/role"
                        ).hasAnyAuthority("ADMINISTRATEUR")
                        .requestMatchers(
                                "/api/user/**"
                        ).hasAnyAuthority("UTILISATEUR")

/*
                        .requestMatchers(
                                "/api/options/**",
                                "/api/lieux/**",
                                "/api/reservations/**",
                                "/api/vehicules/**",
                                "/api/medias/**",
                                "/api/utilisateurs/**",
                                "/api/adresses/**"
                        ).authenticated()
*/

                        .anyRequest().authenticated())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);

        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


/*    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }*/







}
