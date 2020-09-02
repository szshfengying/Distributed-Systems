package com.icbc.distributed.accopen.controller;

import com.icbc.common.utils.R;
import com.icbc.distributed.accopen.entity.ForgetEntity;
import com.icbc.distributed.accopen.service.ForgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/ForgetPassword")
public class ForgetPassword {

    @Autowired
    ForgetService forgetService;

    @RequestMapping("/reset")
    public R Reset(@RequestBody ForgetEntity forgetEntity){
        String newpassword = forgetService.set(forgetEntity);
        if(newpassword.isEmpty()){
            return R.error(500,"输入的信息错误");
        }
        Map map = new HashMap<String,String>();
        map.put("newpassword",newpassword);
        return R.ok(map);
    }
}
