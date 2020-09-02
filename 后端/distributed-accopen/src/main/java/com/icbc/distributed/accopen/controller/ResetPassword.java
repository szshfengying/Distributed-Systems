package com.icbc.distributed.accopen.controller;

import com.icbc.common.utils.R;
import com.icbc.distributed.accopen.entity.ResetEntity;
import com.icbc.distributed.accopen.service.ResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/ResetPassword")
public class ResetPassword {
    @Autowired
    ResetService resetService;
    @RequestMapping("/reset")
    public R reset(@RequestBody ResetEntity resetEntity){
        String newpassword = resetService.reset(resetEntity);
        if(newpassword.isEmpty()){
            return R.error(500,"输入的密码错误");
        }
        Map map = new HashMap<String,String>();
        map.put("newpassword",newpassword);
        return R.ok(map);
    }

}
