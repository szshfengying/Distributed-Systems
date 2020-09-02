package com.icbc.distributed.interest.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icbc.distributed.interest.entity.AccBalanceTableEntity;
import com.icbc.distributed.interest.service.AccBalanceTableService;
import com.icbc.common.utils.PageUtils;
import com.icbc.common.utils.R;



/**
 * 
 *
 * @author ${author}
 * @email ${email}
 * @date 2020-07-20 20:45:08
 */
@RestController
@RequestMapping("interest/accbalancetable")
public class AccBalanceTableController {
    @Autowired
    private AccBalanceTableService accBalanceTableService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = accBalanceTableService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{snId}")
    public R info(@PathVariable("snId") Long snId){
		AccBalanceTableEntity accBalanceTable = accBalanceTableService.getById(snId);

        return R.ok().put("accBalanceTable", accBalanceTable);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AccBalanceTableEntity accBalanceTable){
		accBalanceTableService.save(accBalanceTable);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AccBalanceTableEntity accBalanceTable){
		accBalanceTableService.updateById(accBalanceTable);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] snIds){
		accBalanceTableService.removeByIds(Arrays.asList(snIds));

        return R.ok();
    }

}
