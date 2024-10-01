package com.alex.gamestore.configuration;

import com.alex.gamestore.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@Import(Encoders.class)
public class ApplicationSecurity {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login/**").permitAll()
                .requestMatchers("/**").permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
//        http.cors().and().csrf().disable()
////                .exceptionHandling().authenticationEntryPoint(handler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests().antMatchers("/login/**").permitAll()
//                .antMatchers("/**").permitAll();
//                .anyRequest().authenticated();

        return http.build();
    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(username -> userRepository
//                .findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(
//                        String.format("User: %s, not found", username)
//                )));
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http = http.cors().and().csrf().disable();
//
//        http = http
//                .sessionManagement()
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                                .and();
//
//        http = http
//                .exceptionHandling()
//                        .authenticationEntryPoint(
//                                ((request, response, authException) -> response.sendError(
//                                        HttpServletResponse.SC_UNAUTHORIZED,
//                                        authException.getMessage()
//                                ))
//                        )
//                                .and();
//
//        http.authorizeRequests()
//                        .antMatchers("/login/**").permitAll()
//                        .anyRequest().authenticated();
//
//        http.addFilterBefore(
//                jwtTokenFilter,
//                UsernamePasswordAuthenticationFilter.class
//        );
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
