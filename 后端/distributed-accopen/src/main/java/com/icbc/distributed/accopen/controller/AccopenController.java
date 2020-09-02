package com.icbc.distributed.accopen.controller;

import com.icbc.common.utils.R;
import com.icbc.distributed.accopen.entity.AccInfoTableEntity;
import com.icbc.distributed.accopen.service.RegisterAccService;
import com.icbc.distributed.accopen.vo.request.AccLoginEntity;
import com.icbc.distributed.accopen.vo.request.AccOpenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RefreshScope
@RestController
@RequestMapping(value = "/accOpen")
public class AccOpenController {

    @Autowired
    RegisterAccService registerAccService;

    @RequestMapping(value = "/register")
    public R register(@RequestBody AccOpenEntity accOpenEntity){
        if(accOpenEntity == null){
            return R.error("参数有误");
        }
        String accid = registerAccService.registerAcc(accOpenEntity);
        Map map = new HashMap<String,String>();
        map.put("accid",accid);
        return R.ok(map);
    }

}


