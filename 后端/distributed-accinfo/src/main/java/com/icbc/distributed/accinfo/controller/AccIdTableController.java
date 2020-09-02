package com.icbc.distributed.accinfo.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icbc.distributed.accinfo.entity.AccIdTableEntity;
import com.icbc.distributed.accinfo.service.AccIdTableService;
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
@RequestMapping("accinfo/accidtable")
public class AccIdTableController {
    @Autowired
    private AccIdTableService accIdTableService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = accIdTableService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{snId}")
    public R info(@PathVariable("snId") Long snId){
		AccIdTableEntity accIdTable = accIdTableService.getById(snId);

        return R.ok().put("accIdTable", accIdTable);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AccIdTableEntity accIdTable){
		accIdTableService.save(accIdTable);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AccIdTableEntity accIdTable){
		accIdTableService.updateById(accIdTable);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] snIds){
		accIdTableService.removeByIds(Arrays.asList(snIds));

        return R.ok();
    }

}
