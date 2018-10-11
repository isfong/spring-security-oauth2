package com.isfong.spring_security_oauth2.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class OAuth2AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {
    private static final String DEMO_RESOURCE_ID = "order";
    private final AuthenticationManager authenticationManager;
    private final OAuth2WebResponseExceptionTranslator oAuth2WebResponseExceptionTranslator;
    private final UserDetailsService userDetailsService;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter( ) {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter( ) {
            @Override
            public OAuth2AccessToken enhance( OAuth2AccessToken accessToken, OAuth2Authentication authentication ) {
                String userName = authentication.getUserAuthentication( ).getName( );
                User user = ( User ) authentication.getUserAuthentication( ).getPrincipal( );// 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
                /* 自定义一些token属性 ***/
                final Map< String, Object > additionalInformation = new HashMap<>( );
                additionalInformation.put( "id", "1111100000" );
                additionalInformation.put( "username", userName );
                additionalInformation.put( "roles", user.getAuthorities( ) );
                ( ( DefaultOAuth2AccessToken ) accessToken ).setAdditionalInformation( additionalInformation );
                return super.enhance( accessToken, authentication );
            }
        };
        converter.setSigningKey( "123456" );
        return converter;
    }

    @Override
    public void configure( ClientDetailsServiceConfigurer clients ) throws Exception {
        clients
                .inMemory( )
//                .withClient( "client_1" )
//                .resourceIds( DEMO_RESOURCE_ID )
//                .authorizedGrantTypes( "client_credentials", "refresh_token" )
//                .scopes( "select" )
//                .authorities( "client" )
//                .secret( "123456" )
//                .and( )
                .withClient( "client_2" )
                .resourceIds( DEMO_RESOURCE_ID )
                .authorizedGrantTypes( "password", "refresh_token" )
                .scopes( "select" )
                .authorities( "client" )
                .secret( "123456" )
                .accessTokenValiditySeconds( 30 )
                .refreshTokenValiditySeconds( 86400 );
    }

    @Override
    public void configure( AuthorizationServerEndpointsConfigurer endpoints ) {
        endpoints
                .tokenStore( new JwtTokenStore( accessTokenConverter( ) ) )
                .authenticationManager( authenticationManager )
                .accessTokenConverter( accessTokenConverter( ) )
                .userDetailsService( userDetailsService )
                .exceptionTranslator( oAuth2WebResponseExceptionTranslator );
    }

    @Override
    public void configure( AuthorizationServerSecurityConfigurer oauthServer ) {
        //允许表单认证
        oauthServer.allowFormAuthenticationForClients( );
    }


}
