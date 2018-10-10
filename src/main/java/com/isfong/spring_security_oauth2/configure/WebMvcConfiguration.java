package com.isfong.spring_security_oauth2.configure;

import com.isfong.spring_security_oauth2.web.aop.OAuth2AuthorizationServerAspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors( InterceptorRegistry registry ) {
        registry.addInterceptor( new OAuth2AuthorizationServerAspect( ) ).addPathPatterns( "/oauth/**" );
    }
}
