package com.icbc.distributed.interest.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icbc.common.utils.PageUtils;
import com.icbc.common.utils.Query;

import com.icbc.distributed.interest.dao.AccDetailsTableDao;
import com.icbc.distributed.interest.entity.AccDetailsTableEntity;
import com.icbc.distributed.interest.service.AccDetailsTableService;


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