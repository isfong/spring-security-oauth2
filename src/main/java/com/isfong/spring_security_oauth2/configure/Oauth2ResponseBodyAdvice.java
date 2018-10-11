package com.isfong.spring_security_oauth2.configure;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
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
        if ( JSON.parseObject( JSON.toJSONString( o ) ).get( "error" ) != null ) return o;
        JSONObject response = new JSONObject( );
        response.put( "code", 200 );
        response.put( "msg", "OK" );
        response.put( "sub_code", "request_success" );
        response.put( "sub_msg", "请求操作成功" );
        response.put( "result", o );
        return response;
    }
}
