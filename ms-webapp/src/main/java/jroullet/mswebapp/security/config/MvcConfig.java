//package jroullet.mswebapp.security.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//
//public class MvcConfig implements WebMvcConfigurer {
//    // Use Spring security included controller for login: gives additional security
//    public void addViewControllers(ViewControllerRegistry registry) {
//        // "signin" refers to template, only "signin" works, "login" is used for something else
//        // never use this URI in controllers, this one bypasses the rest on this specific URI
//        registry.addViewController("/signin").setViewName("signin");
//    }
//
//
//}
