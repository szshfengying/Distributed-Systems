package com.icbc.distributed.accopen.controller;

import com.icbc.common.exception.MsgCode.BaseCode;
import com.icbc.common.utils.R;
import com.icbc.distributed.accopen.entity.AccInfoTableEntity;
import com.icbc.distributed.accopen.jwt.JwtUtil;
import com.icbc.distributed.accopen.service.LoginAccService;
import com.icbc.distributed.accopen.vo.request.AccLoginEntity;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//@RefreshScope
@RestController
@RequestMapping("/AccLogin")
public class AccLoginController {
    @Autowired
    LoginAccService loginAccService;

    @Autowired
    JwtUtil jwtUtil;

    @RequestMapping("/login")
    public R login(@RequestBody AccLoginEntity accLoginEntity){
        //loginAccService.loginAcc(accLoginEntity);
        String jwt=jwtUtil.createJwt(accLoginEntity.getAccid());
        String ret = loginAccService.loginAcc(accLoginEntity);
        Map map = new HashMap<String,String>();
        if(ret.equals("登录成功")){
            map.put("token",jwt);
        //map.put("claims",claims);
            map.put("code",10009);
            return R.ok(map);}
        else {
            map.put("ret",ret);
            map.put("code",10008);
            map.put("msg","error");
            return R.error(map);}
    }
}
