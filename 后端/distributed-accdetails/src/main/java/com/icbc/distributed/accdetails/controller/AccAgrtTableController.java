package com.icbc.distributed.accdetails.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icbc.distributed.accdetails.entity.AccAgrtTableEntity;
import com.icbc.distributed.accdetails.service.AccAgrtTableService;
import com.icbc.common.utils.PageUtils;
import com.icbc.common.utils.R;

import javax.naming.NameNotFoundException;


/**
 * 
 *
 * @author ${author}
 * @email ${email}
 * @date 2020-07-20 20:19:53
 */
@RefreshScope
@RestController
@RequestMapping("accdetails/accagrttable")
public class AccAgrtTableController {
    @Autowired
    private AccAgrtTableService accAgrtTableService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = accAgrtTableService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{accId}")
    public R info(@PathVariable("accId") Long accId){
		AccAgrtTableEntity accAgrtTable = accAgrtTableService.getById(accId);

        return R.ok().put("accAgrtTable", accAgrtTable);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AccAgrtTableEntity accAgrtTable){
		accAgrtTableService.save(accAgrtTable);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AccAgrtTableEntity accAgrtTable){
		accAgrtTableService.updateById(accAgrtTable);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] accIds){
		accAgrtTableService.removeByIds(Arrays.asList(accIds));

        return R.ok();
    }

}
