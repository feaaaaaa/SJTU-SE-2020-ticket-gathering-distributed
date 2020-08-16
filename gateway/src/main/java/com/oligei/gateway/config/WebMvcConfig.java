package com.oligei.gateway.config;

import com.oligei.gateway.interceptor.AuthenInterceptor;
import com.oligei.gateway.interceptor.AuthorInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/user/Register");
        excludePath.add("/user/Login");
        excludePath.add("/user/ExistsByUsername");
        excludePath.add("/activity/search");
        excludePath.add("/activity/FindActivityByCategory");
        excludePath.add("/index.html");
        excludePath.add("/activity/FindActivityByCategoryHome");
        excludePath.add("/hi");
        excludePath.add("/");
        excludePath.add("/activity/initActivity");
        excludePath.add("/activity/initSearchIndex");
        excludePath.add("/activity/clear");

        excludePath.add("/swagger-ui.html");
        excludePath.add("/webjars/**");
        excludePath.add("/favicon.ico");
        excludePath.add("/v2/api-docs");
        excludePath.add("/swagger-resources/**");
        excludePath.add("/auction/get");
        //excludePath.add("/actitem/detail");
        registry.addInterceptor(new AuthenInterceptor()).addPathPatterns("/**")
                                                        .excludePathPatterns(excludePath);
        registry.addInterceptor(new AuthorInterceptor()).addPathPatterns("/activity/add")
                                                        .addPathPatterns("/activity/delete");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT","PATCH")
                .maxAge(3600);
    }
}
