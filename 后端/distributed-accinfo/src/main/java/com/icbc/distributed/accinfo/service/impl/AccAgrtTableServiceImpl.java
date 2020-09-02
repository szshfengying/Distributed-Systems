package com.icbc.distributed.accinfo.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icbc.common.utils.PageUtils;
import com.icbc.common.utils.Query;

import com.icbc.distributed.accinfo.dao.AccAgrtTableDao;
import com.icbc.distributed.accinfo.entity.AccAgrtTableEntity;
import com.icbc.distributed.accinfo.service.AccAgrtTableService;


@Service("accAgrtTableService")
public class AccAgrtTableServiceImpl extends ServiceImpl<AccAgrtTableDao, AccAgrtTableEntity> implements AccAgrtTableService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AccAgrtTableEntity> page = this.page(
                new Query<AccAgrtTableEntity>().getPage(params),
                new QueryWrapper<AccAgrtTableEntity>()
        );

        return new PageUtils(page);
    }

}