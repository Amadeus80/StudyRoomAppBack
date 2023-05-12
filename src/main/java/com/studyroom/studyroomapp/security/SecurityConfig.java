package com.studyroom.studyroomapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import com.studyroom.studyroomapp.auth.filter.JWTAuthenticationFilter;
import com.studyroom.studyroomapp.auth.filter.JWTAuthorizationFilter;
import com.studyroom.studyroomapp.auth.service.JWTService;
import com.studyroom.studyroomapp.userDetails.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean 
    public static BCryptPasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/login*", "/api/login/**", "/api/user/add").permitAll()
                .requestMatchers("/api/user/**").hasAnyRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
				.anyRequest().authenticated()
			)
            .headers(headers -> headers.frameOptions().disable())
            /* .apply(new MyCustomDsl())
            .and() */
            .csrf(csrf -> {csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")); csrf.disable();})
            .sessionManagement(sessionManagent -> sessionManagent.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtService))
            .addFilter(new JWTAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), jwtService));
		return http.build();
	}
}
