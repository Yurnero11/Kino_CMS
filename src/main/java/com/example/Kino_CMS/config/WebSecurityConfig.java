package com.example.Kino_CMS.config;

import com.example.Kino_CMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    @Autowired
    private UserRepository userRepository;

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().hasRole("ADMIN")
                ).formLogin(form -> form
                        .loginPage("/admin/login")
                        .defaultSuccessUrl("/admin/home", true) // Redirect to /admin/home after successful login
                        .permitAll()
                )
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/admin/user/{id}/edit"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/admin/cinemas/{cinema_id}/edit"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/admin/cinemas/{cinema_id}/edit/hall-add"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/admin/cinemas/{cinema_id}/edit/{hall_id}/edit"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/admin/send-email"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/admin/file-delete/{fileId}"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/admin/pages/contacts/add"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/admin/pages/contacts/{cinema_id}/remove"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/admin/pages/contacts/updateStatus"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/admin/pages/{page_id}/delete"))
                .csrf((csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().hasRole("ADMIN")
                ).formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true) // Redirect to /admin/home after successful login
                        .permitAll()
                )
                .httpBasic(withDefaults());
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            com.example.Kino_CMS.entity.User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            return User.withUsername(user.getEmail())
                    .password(user.getPasswordHash())
                    .roles("ADMIN") // Здесь укажите роли пользователя, если они хранятся в БД
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}