package com.isfong.spring_security_oauth2.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class OAuth2ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
    private static final String DEMO_RESOURCE_ID = "order";
    private final OAuth2WebResponseExceptionTranslator oAuth2WebResponseExceptionTranslator;

    @Override
    public void configure( ResourceServerSecurityConfigurer resources ) {
        OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint = new OAuth2AuthenticationEntryPoint( );
        OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler = new OAuth2AccessDeniedHandler( );
        oAuth2AuthenticationEntryPoint.setExceptionTranslator( oAuth2WebResponseExceptionTranslator );
        oAuth2AccessDeniedHandler.setExceptionTranslator( oAuth2WebResponseExceptionTranslator );

        resources
                .resourceId( DEMO_RESOURCE_ID )
                .stateless( true )
                .authenticationEntryPoint( oAuth2AuthenticationEntryPoint )
                .accessDeniedHandler( oAuth2AccessDeniedHandler );
    }

    @Override
    public void configure( HttpSecurity http ) throws Exception {
        http
                .authorizeRequests( )
                .antMatchers( "/order/**" )
                .authenticated( );//配置order访问控制，必须认证过后才可以访问

    }
}
