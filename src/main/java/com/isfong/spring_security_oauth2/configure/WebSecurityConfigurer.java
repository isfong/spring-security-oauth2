package com.isfong.spring_security_oauth2.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder( ) {
        return new PasswordEncoder( ) {
            @Override
            public String encode( CharSequence charSequence ) {
                return charSequence.toString( );
            }

            @Override
            public boolean matches( CharSequence charSequence, String s ) {
                return s.equals( charSequence.toString( ) );
            }
        };
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager( ) throws Exception {
        return super.authenticationManager( );
    }


    @Bean
    @Override
    protected UserDetailsService userDetailsService( ) {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager( );
        inMemoryUserDetailsManager.createUser( User.withUsername( "user_1" ).password( "123456" ).authorities( "USER" ).build( ) );
        inMemoryUserDetailsManager.createUser( User.withUsername( "user_2" ).password( "123456" ).authorities( "USER" ).build( ) );
        return inMemoryUserDetailsManager;
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http
                .requestMatchers( )
                .anyRequest( )
                .and( )
                .authorizeRequests( )
                .antMatchers( "/oauth/*" )
                .permitAll( );
    }
}
