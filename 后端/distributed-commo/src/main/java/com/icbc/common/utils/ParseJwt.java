package com.icbc.common.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


//@Service
//@FeignClient("distributed-accopen")
public class ParseJwt {
    //解析token
    public static Claims parseJWT(String jwtStr){
        return Jwts.parser()
                .setSigningKey("wzt->123")
                .parseClaimsJws(jwtStr)
                .getBody();
    }


}
