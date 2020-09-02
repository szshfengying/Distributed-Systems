package com.icbc.distributed.accinfo.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icbc.distributed.accinfo.entity.AccDetailsTableEntity;
import com.icbc.distributed.accinfo.service.AccDetailsTableService;
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
@RequestMapping("accinfo/accdetailstable")
public class AccDetailsTableController {
    @Autowired
    private AccDetailsTableService accDetailsTableService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = accDetailsTableService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{departId}")
    public R info(@PathVariable("departId") Integer departId){
		AccDetailsTableEntity accDetailsTable = accDetailsTableService.getById(departId);

        return R.ok().put("accDetailsTable", accDetailsTable);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AccDetailsTableEntity accDetailsTable){
		accDetailsTableService.save(accDetailsTable);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AccDetailsTableEntity accDetailsTable){
		accDetailsTableService.updateById(accDetailsTable);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] departIds){
		accDetailsTableService.removeByIds(Arrays.asList(departIds));

        return R.ok();
    }

}
