package com.isfong.spring_security_oauth2.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class OAuth2ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
    private static final String DEMO_RESOURCE_ID = "order";

    @Override
    public void configure( ResourceServerSecurityConfigurer resources ) {
        resources
                .resourceId( DEMO_RESOURCE_ID )
                .stateless( true );
    }

    @Override
    public void configure( HttpSecurity http ) throws Exception {
        http
                .authorizeRequests( )
                .antMatchers( "/order/**" )
                .authenticated( );//配置order访问控制，必须认证过后才可以访问

    }
}
