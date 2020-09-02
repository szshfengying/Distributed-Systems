package com.icbc.distributed.accopen.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("wzt")
    private String key;

    @Value("3600")
    private long ttl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public String createJwt(String id){
        ttl=6000000;
        long nowm=System.currentTimeMillis();
        Date now=new Date(nowm);
        JwtBuilder builder= Jwts.builder().setId(id).signWith(SignatureAlgorithm.HS256,"wzt->123");
        if(ttl>0){
            builder.setExpiration(new Date(nowm+ttl));
        }
        return builder.compact();
    }

    public Claims parseJWT(String jwtStr){
        return Jwts.parser()
                .setSigningKey("wzt->123")
                .parseClaimsJws(jwtStr)
                .getBody();
    }
}
