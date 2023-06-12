package com.studyroom.studyroomapp.security;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.studyroom.studyroomapp.auth.filter.JWTAuthenticationFilter;
import com.studyroom.studyroomapp.auth.filter.JWTAuthorizationFilter;
import com.studyroom.studyroomapp.auth.service.JWTService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JWTService jwtService;

    @Value("${allowed.url}")
    private String allowedUrl;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* Se define la configuración de seguridad, rutas permitidas a todos, las protegidas con autenticación y las que solo pueden acceder los admin.
        También se indican los filtros de los JWT que se encargan de en cada petición que llega al servidor comporbar si el token es válido 
        y no esta caducado para luego proceder a autenticar al usuario del token 
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/login*", "/api/login/**", "/api/user/add", "/api/contacto/add", "/comunicados/**", "/api/comunicados/**").permitAll()
                        .antMatchers(HttpMethod.OPTIONS, "*").permitAll()
                        .antMatchers("/api/user/find-user-logeado").authenticated()
                        .antMatchers("/api/user/editUsernameUsuario").authenticated()
                        .antMatchers("/api/user/editPasswordUsuario").authenticated()
                        .antMatchers("/api/user/**").hasAnyRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> {
                    csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"));
                    csrf.disable();
                })
                .headers(h -> h.frameOptions().disable())
                .sessionManagement(
                        sessionManagent -> sessionManagent.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(
                        new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtService))
                .addFilterBefore(
                        new JWTAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), jwtService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /* Configuración de CORS */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowedHeaders(Arrays.asList("Origin,Accept", "X-Requested-With", "Content-Type",
                "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization", "X-Auth-Token"));
        cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Authorization"));
        cc.setAllowedOriginPatterns(Arrays.asList("*"));
        /* cc.setAllowedOrigins(Arrays.asList(allowedUrl)); */
        cc.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "PATCH", "DELETE"));
        /* cc.addAllowedOrigin("*"); */
        cc.setMaxAge(Duration.ZERO);
        cc.setAllowCredentials(Boolean.TRUE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cc);
        return source;
    }
}
