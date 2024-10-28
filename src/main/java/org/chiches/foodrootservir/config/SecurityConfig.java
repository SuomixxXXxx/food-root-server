package org.chiches.foodrootservir.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(conf -> conf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/v1/auth/**").permitAll()
                        //FIXME: NOT SUITABLE FOR REAL USAGE, DEV ONLY
                                .anyRequest().permitAll()

//                        //.requestMatchers("/api/v1/user/**").permitAll()
//
//                        .requestMatchers("/files/**").permitAll()
//
//                        .requestMatchers("/api/v1/staff/**").hasAnyRole(ADMIN.name(), STAFF.name())
//
//                        .requestMatchers(HttpMethod.GET,"/api/v1/staff/**").hasAnyAuthority(ADMIN_READ.name(),STAFF_READ.name())
//                        .requestMatchers(HttpMethod.POST,"/api/v1/staff/**").hasAnyAuthority(ADMIN_CREATE.name(),STAFF_CREATE.name())
//                        .requestMatchers(HttpMethod.PUT,"/api/v1/staff/**").hasAnyAuthority(ADMIN_UPDATE.name(),STAFF_UPDATE.name())
//                        .requestMatchers(HttpMethod.DELETE,"/api/v1/staff/**").hasAnyAuthority(ADMIN_DELETE.name(),STAFF_DELETE.name())
//
//                        .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
//
//                        .requestMatchers(HttpMethod.GET,"/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
//                        .requestMatchers(HttpMethod.POST,"/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
//                        .requestMatchers(HttpMethod.PUT,"/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
//                        .requestMatchers(HttpMethod.DELETE,"/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
//
                             //   .anyRequest().permitAll()

                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
