package com.icbc.distributed.accdetails.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icbc.common.utils.PageUtils;
import com.icbc.common.utils.Query;

import com.icbc.distributed.accdetails.dao.AccDetailsTableDao;
import com.icbc.distributed.accdetails.entity.AccDetailsTableEntity;
import com.icbc.distributed.accdetails.service.AccDetailsTableService;


@Service("accDetailsTableService")
public class AccDetailsTableServiceImpl extends ServiceImpl<AccDetailsTableDao, AccDetailsTableEntity> implements AccDetailsTableService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AccDetailsTableEntity> page = this.page(
                new Query<AccDetailsTableEntity>().getPage(params),
                new QueryWrapper<AccDetailsTableEntity>()
        );

        return new PageUtils(page);
    }

}