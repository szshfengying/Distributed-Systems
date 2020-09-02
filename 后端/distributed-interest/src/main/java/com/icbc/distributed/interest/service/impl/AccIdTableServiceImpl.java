package com.icbc.distributed.interest.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icbc.common.utils.PageUtils;
import com.icbc.common.utils.Query;

import com.icbc.distributed.interest.dao.AccIdTableDao;
import com.icbc.distributed.interest.entity.AccIdTableEntity;
import com.icbc.distributed.interest.service.AccIdTableService;


@Service("accIdTableService")
public class AccIdTableServiceImpl extends ServiceImpl<AccIdTableDao, AccIdTableEntity> implements AccIdTableService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AccIdTableEntity> page = this.page(
                new Query<AccIdTableEntity>().getPage(params),
                new QueryWrapper<AccIdTableEntity>()
        );

        return new PageUtils(page);
    }

}