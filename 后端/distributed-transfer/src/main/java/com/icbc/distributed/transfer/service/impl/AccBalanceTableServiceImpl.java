package com.icbc.distributed.transfer.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icbc.common.utils.PageUtils;
import com.icbc.common.utils.Query;

import com.icbc.distributed.transfer.dao.AccBalanceTableDao;
import com.icbc.distributed.transfer.entity.AccBalanceTableEntity;
import com.icbc.distributed.transfer.service.AccBalanceTableService;


@Service("accBalanceTableService")
public class AccBalanceTableServiceImpl extends ServiceImpl<AccBalanceTableDao, AccBalanceTableEntity> implements AccBalanceTableService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AccBalanceTableEntity> page = this.page(
                new Query<AccBalanceTableEntity>().getPage(params),
                new QueryWrapper<AccBalanceTableEntity>()
        );

        return new PageUtils(page);
    }

}