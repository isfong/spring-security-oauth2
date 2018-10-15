package com.isfong.spring_security_oauth2.configure;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

@Component
public class OAuth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator< Object > {
    @Override
    public ResponseEntity< Object > translate( Exception e ) {
        System.err.println( "[OAuth2异常处理-OAuth2WebResponseExceptionTranslator] " + e.getClass( ).getName( ) );

        JSONObject body = new JSONObject( );
        body.put( "code", 400 );
        body.put( "msg", "错误" );
        body.put( "sub_msg", e.getMessage( ) );
        if ( e.getCause( ) instanceof InvalidTokenException ) {
            InvalidTokenException invalidTokenException = ( InvalidTokenException ) e.getCause( );
            body.put( "sub_code", invalidTokenException.getOAuth2ErrorCode( ) );
        }
        body.put( "result", new JSONObject( ) );
        return new ResponseEntity<>( body, HttpStatus.EXPECTATION_FAILED );
    }

}
