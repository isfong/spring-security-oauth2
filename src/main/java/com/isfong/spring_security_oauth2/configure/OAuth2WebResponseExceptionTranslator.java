package com.isfong.spring_security_oauth2.configure;

import com.alibaba.fastjson.JSON;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OAuth2WebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {


    @Override
    public ResponseEntity< OAuth2Exception > translate( Exception e ) throws Exception {
        ResponseEntity< OAuth2Exception > superResponseEntity = super.translate( e );
        System.err.println( JSON.toJSONString( superResponseEntity.getBody( ) ) );
        Objects.requireNonNull( superResponseEntity.getBody( ) ).addAdditionalInformation( "code", "400" );
        superResponseEntity.getBody( ).addAdditionalInformation( "msg", "错误" );
        superResponseEntity.getBody( ).addAdditionalInformation( "sub_code", superResponseEntity.getBody( ).getOAuth2ErrorCode( ) );
        superResponseEntity.getBody( ).addAdditionalInformation( "sub_msg", superResponseEntity.getBody( ).getLocalizedMessage( ) );
        return superResponseEntity;
    }
}


/*
@Data
@EqualsAndHashCode( callSuper = true )
class MyOAuth2Exception extends OAuth2Exception {
    private Map< String, String > additionalInformation;

    MyOAuth2Exception( String msg ) {
        super( msg );
        this.additionalInformation = super.getAdditionalInformation( );
    }
}*/
