package com.isfong.spring_security_oauth2.web.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResController {

    @GetMapping( "product/{id}" )
    public Object product( @PathVariable long id ) {
        int i = 1/0;
        Authentication authentication = SecurityContextHolder.getContext( ).getAuthentication( );
        JSONObject jsonObject = new JSONObject( );
        jsonObject.put( "product_id", id );
        jsonObject.put( "authentication", authentication );
        return jsonObject;
    }

    @PostAuthorize( "hasRole('ROLE_ADMIN')" )
    @GetMapping( "order/{id}" )
    public Object order( @PathVariable long id ) {
        Authentication authentication = SecurityContextHolder.getContext( ).getAuthentication( );
        JSONObject jsonObject = new JSONObject( );
        jsonObject.put( "order_id", id );
        jsonObject.put( "authentication", authentication );
        return jsonObject;
    }


}
