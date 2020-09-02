package com.icbc.distributed.check.controller;

import com.icbc.common.utils.R;
import com.icbc.distributed.check.entity.AccBalanceTableEntity;
import com.icbc.distributed.check.entity.AccDetailsTableEntity;
import com.icbc.distributed.check.entity.NettingEntity;
import com.icbc.distributed.check.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RefreshScope
@RestController
@RequestMapping("/AccCheck")
public class AccCheckController {
    @Autowired
    CheckService checkService;
    @RequestMapping("/check")
    public R check() {
       checkService.CheckResult();
           return R.ok();
    }
}