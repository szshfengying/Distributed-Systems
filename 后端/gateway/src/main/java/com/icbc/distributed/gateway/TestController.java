package com.icbc.distributed.gateway;



import com.icbc.distributed.gateway.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RefreshScope
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${mysql.user}")
    private String user;

    @Value("${mysql.password}")
    private String password;

    @RequestMapping("/test1")
    public R list(@RequestParam Map<String, Object> params){
        return R.ok().put("root", user).put("password",password);
    }
}
