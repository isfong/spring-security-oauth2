package com.isfong.spring_security_oauth2.configure;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice( basePackageClasses = { TokenEndpoint.class } )
public class Oauth2ResponseBodyAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports( MethodParameter methodParameter, Class aClass ) {
        return true;
    }

    @Override
    public Object beforeBodyWrite( Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse ) {
        System.err.println( "[认证授权-Oauth2ResponseBodyAdvice] " + o.getClass( ).getName( ) );
        int code = 200;
        String msg = "成功";
        String sub_code = "request_success";
        String sub_msg = "请求操作成功";
        if ( o instanceof InvalidGrantException ) {
            InvalidGrantException invalidGrantException = ( InvalidGrantException ) o;
            code = invalidGrantException.getHttpErrorCode( );
            msg = "错误";
            sub_code = invalidGrantException.getOAuth2ErrorCode( );
            sub_msg = invalidGrantException.getLocalizedMessage( );
        } else if ( o instanceof InvalidTokenException ) {
            InvalidTokenException invalidTokenException = ( InvalidTokenException ) o;
            code = invalidTokenException.getHttpErrorCode( );
            msg = "错误";
            sub_code = invalidTokenException.getOAuth2ErrorCode( );
            sub_msg = invalidTokenException.getLocalizedMessage( );
        }
        JSONObject response = new JSONObject( );
        response.put( "code", code );
        response.put( "msg", msg );
        response.put( "sub_code", sub_code );
        response.put( "sub_msg", sub_msg );
        response.put( "result", o );
        return response;
    }
}
