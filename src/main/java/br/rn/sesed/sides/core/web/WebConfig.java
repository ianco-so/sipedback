package br.rn.sesed.sides.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.rn.sesed.sides.core.security.interceptor.SecurityInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SecurityInterceptor securityInterceptor;


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // registry.addViewController("/login")
        //         .setViewName("login");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/swagger-ui/**", "/v2/api-docs", "/webjars/**", "/swagger-resources/**")
        .excludePathPatterns("/usuario/login", "/error");
    }

}
