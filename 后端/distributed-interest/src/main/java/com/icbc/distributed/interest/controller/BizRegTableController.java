package com.icbc.distributed.interest.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icbc.distributed.interest.entity.BizRegTableEntity;
import com.icbc.distributed.interest.service.BizRegTableService;
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
@RequestMapping("interest/bizregtable")
public class BizRegTableController {
    @Autowired
    private BizRegTableService bizRegTableService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bizRegTableService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{snId}")
    public R info(@PathVariable("snId") Long snId){
		BizRegTableEntity bizRegTable = bizRegTableService.getById(snId);

        return R.ok().put("bizRegTable", bizRegTable);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BizRegTableEntity bizRegTable){
		bizRegTableService.save(bizRegTable);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody BizRegTableEntity bizRegTable){
		bizRegTableService.updateById(bizRegTable);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] snIds){
		bizRegTableService.removeByIds(Arrays.asList(snIds));

        return R.ok();
    }

}
