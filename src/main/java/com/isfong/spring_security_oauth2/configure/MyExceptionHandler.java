package com.isfong.spring_security_oauth2.configure;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler( Exception.class )
    public Object handler( Exception e ) {
        System.err.println( "[全局异常处理-MyExceptionHandler] " + e.getClass( ).getName( ) );

        JSONObject body = new JSONObject( );
        body.put( "code", 500 );
        body.put( "msg", "异常" );
        //noinspection ReplaceAllDot
        body.put( "sub_code", e.getClass( ).getName( ) );
        body.put( "sub_msg", e.getMessage( ) );

        if ( e instanceof AccessDeniedException ) {
            body.put( "code", 400 );
            body.put( "msg", "错误" );
            body.put( "sub_code", "access_denied" );
        }

        body.put( "result", new JSONObject( ) );

        return body;
    }
}
