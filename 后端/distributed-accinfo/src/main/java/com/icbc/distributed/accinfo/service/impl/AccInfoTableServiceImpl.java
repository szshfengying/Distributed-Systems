package com.icbc.distributed.accinfo.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icbc.common.utils.PageUtils;
import com.icbc.common.utils.Query;

import com.icbc.distributed.accinfo.dao.AccInfoTableDao;
import com.icbc.distributed.accinfo.entity.AccInfoTableEntity;
import com.icbc.distributed.accinfo.service.AccInfoTableService;


@Service("accInfoTableService")
public class AccInfoTableServiceImpl extends ServiceImpl<AccInfoTableDao, AccInfoTableEntity> implements AccInfoTableService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AccInfoTableEntity> page = this.page(
                new Query<AccInfoTableEntity>().getPage(params),
                new QueryWrapper<AccInfoTableEntity>()
        );

        return new PageUtils(page);
    }

}