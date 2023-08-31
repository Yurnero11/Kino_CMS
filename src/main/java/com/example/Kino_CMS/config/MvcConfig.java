package com.example.Kino_CMS.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/login").setViewName("/admin/layout/login");
        registry.addViewController("/admin/logout").setViewName("/admin/layout/login");
        registry.addViewController("/admin").setViewName("redirect:/admin/login");
        registry.addViewController("/admin/logout").setViewName("redirect:/admin/login");

        registry.addViewController("/logout").setViewName("redirect:/login");
        registry.addViewController("/logout").setViewName("/public/login-page/login");
        registry.addViewController("/home").setViewName("/public/index-page/index");

        registry.addViewController("/").setViewName("/public/index-page/index");
        registry.addViewController("/").setViewName("redirect:/home");
        registry.addViewController("/login").setViewName("/public/login-page/login");
        registry.addViewController("/register").setViewName("/public/login-page/registration");
    }

    @Value("${spring.pathImg}")
    String pathPhotos;

    @Value("${spring.pathFiles}")
    String pathFiles;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + pathPhotos)
                .setCachePeriod(0);

        registry.addResourceHandler("/upload/email_upload/**")
                .addResourceLocations("file:" + pathFiles)
                .setCachePeriod(0);

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
    }
}