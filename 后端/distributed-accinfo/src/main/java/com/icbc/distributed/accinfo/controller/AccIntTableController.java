package com.icbc.distributed.accinfo.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icbc.distributed.accinfo.entity.AccIntTableEntity;
import com.icbc.distributed.accinfo.service.AccIntTableService;
import com.icbc.common.utils.PageUtils;
import com.icbc.common.utils.R;



/**
 * 
 *
 * @author ${author}
 * @email ${email}
 * @date 2020-07-20 20:31:15
 */
@RestController
@RequestMapping("accinfo/accinttable")
public class AccIntTableController {
    @Autowired
    private AccIntTableService accIntTableService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = accIntTableService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{accId}")
    public R info(@PathVariable("accId") Long accId){
		AccIntTableEntity accIntTable = accIntTableService.getById(accId);

        return R.ok().put("accIntTable", accIntTable);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AccIntTableEntity accIntTable){
		accIntTableService.save(accIntTable);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AccIntTableEntity accIntTable){
		accIntTableService.updateById(accIntTable);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] accIds){
		accIntTableService.removeByIds(Arrays.asList(accIds));

        return R.ok();
    }

}
