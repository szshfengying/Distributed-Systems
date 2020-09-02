package com.icbc.distributed.interest.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icbc.common.utils.PageUtils;
import com.icbc.common.utils.Query;

import com.icbc.distributed.interest.dao.BizRegTableDao;
import com.icbc.distributed.interest.entity.BizRegTableEntity;
import com.icbc.distributed.interest.service.BizRegTableService;


@Service("bizRegTableService")
public class BizRegTableServiceImpl extends ServiceImpl<BizRegTableDao, BizRegTableEntity> implements BizRegTableService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BizRegTableEntity> page = this.page(
                new Query<BizRegTableEntity>().getPage(params),
                new QueryWrapper<BizRegTableEntity>()
        );

        return new PageUtils(page);
    }

}