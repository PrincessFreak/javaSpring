package com.example.unistore.config;


import com.example.unistore.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{
    public final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncode() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
        http
                .authorizeHttpRequests()
                .requestMatchers("/users/adminIndex", "/products/productAdd", "/products/productEdit").hasRole("ADMIN")
                .requestMatchers("/", "/index", "/users/userRegistration", "/users/userAuthorization", "/products/productView/{id}", "/productView/{id}", "/error", "/static/**", "/img/**").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
//                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/users/userAuthorization")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/user account", true) //"/users/userIndex"
                .failureUrl("/users/userAuthorization?error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");
        return http.build();
    }

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncode());
    }
}
