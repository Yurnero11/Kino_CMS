package com.example.Kino_CMS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        http
                .addFilterBefore(filter, CsrfFilter.class)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home",
                                "/customers/**", "/dashboard/**",
                                "/error/**", "/fragments/**",
                                "/bower_components/**", "/dist/**", "/js/**",
                                "/plugins/**", "/customers/user-edit", "/films/**", "/images/**", "/cinemas/**")
                        .permitAll() // Добавьте пути к CSS и JS файлам
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true) // Установить /home в качестве страницы по умолчанию после успешного входа
                        .permitAll()
                )
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/user/{id}/edit"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/cinemas/{cinema_id}/edit"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/cinemas/{cinema_id}/edit/hall-add"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/cinemas/{cinema_id}/edit/{hall_id}/edit"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/send-email"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/file-delete/{fileId}"))
                .csrf((csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("Admin")
                        .password("admin")
                        .roles("ADMIN")
                        .build();

        UserDetails user2 =
                User.withDefaultPasswordEncoder()
                        .username("Director")
                        .password("director")
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user, user2);
    }
}