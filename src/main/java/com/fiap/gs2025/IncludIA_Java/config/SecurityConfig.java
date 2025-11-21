package com.fiap.gs2025.IncludIA_Java.config;

import com.fiap.gs2025.IncludIA_Java.security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Rotas Públicas (Login e Cadastro)
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register-candidate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register-recruiter").permitAll()

                        // Outras Rotas Públicas
                        .requestMatchers(HttpMethod.POST, "/empresas").permitAll()

                        // CORREÇÃO AQUI: Adicionado "/admin" nas rotas de GET para liberar a listagem
                        .requestMatchers(HttpMethod.GET, "/admin/skills").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/idiomas").permitAll()

                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/health").permitAll()

                        // Swagger e Documentação
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        .requestMatchers("/error").permitAll()

                        // Rotas Protegidas (POST em /admin continua restrito a RECRUITER)
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("RECRUITER")

                        // Rotas de Candidato
                        .requestMatchers("/profile/**").hasRole("CANDIDATE")
                        .requestMatchers("/save/job").hasRole("CANDIDATE")
                        .requestMatchers("/swipe/candidate").hasRole("CANDIDATE")
                        .requestMatchers("/matches/my-matches").hasRole("CANDIDATE")
                        .requestMatchers("/view/my-profile-views").hasRole("CANDIDATE")

                        // Rotas de Recrutador
                        .requestMatchers("/vagas/**").hasRole("RECRUITER")
                        .requestMatchers("/save/candidate").hasRole("RECRUITER")
                        .requestMatchers("/swipe/recruiter/**").hasRole("RECRUITER")
                        .requestMatchers("/view/profile/**").hasRole("RECRUITER")
                        .requestMatchers("/recruiter-profile/me").hasRole("RECRUITER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}