package com.kadry.picturePublishingService.configs;

import com.kadry.picturePublishingService.core.security.AuthJwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig  {
    @Bean
    @Profile("dev")
    public SecurityFilterChain anonymousFilterChainWithH2(HttpSecurity http, AuthJwtTokenFilter authJwtTokenFilter) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers(toH2Console()).permitAll());
        getHttpSecurityConfig(http, authJwtTokenFilter);
        return http.build();
    }

    @Bean
    @Profile("prod")
    public SecurityFilterChain anonymousFilterChain(HttpSecurity http, AuthJwtTokenFilter authJwtTokenFilter) throws Exception {
        return getHttpSecurityConfig(http, authJwtTokenFilter).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }

    private HttpSecurity getHttpSecurityConfig(HttpSecurity http , AuthJwtTokenFilter authJwtTokenFilter) throws Exception {
        var whitelist = new String[] {"/api/signup", "/api/signin", "/api/pictures",
                "/api/picture/*", "/swagger-ui/**", "/swagger-ui.html", "/api-docs/**", "/api-docs.yaml",
                "/v3/api-docs/**", "/webjars/**","/v3/api-docs.yaml", "/error"};
        var adminAccessOnly = new String[] {"/api/pending-pictures", "/api/pending-picture/*"};

        http
                .authorizeHttpRequests(auth -> auth.requestMatchers(whitelist).permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers(adminAccessOnly).hasRole("ADMIN"))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .headers((headers)-> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .addFilterBefore(authJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http;
    }

    @Bean
    public CorsFilter corsFilter( ) {
        var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        
        config.addAllowedOriginPattern("*");

        config.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

}


