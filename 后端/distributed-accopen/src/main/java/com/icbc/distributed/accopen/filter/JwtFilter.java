package com.icbc.distributed.accopen.filter;


import com.icbc.distributed.accopen.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截器");
        final String token = request.getHeader("Authorization");
        if (token!=null) {

            Claims claims = jwtUtil.parseJWT(token);
            request.setAttribute("claims", claims);

        }
        return true;
    }
}
