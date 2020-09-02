package com.icbc.distributed.accdetails.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icbc.common.utils.PageUtils;
import com.icbc.common.utils.Query;

import com.icbc.distributed.accdetails.dao.BizRegTableDao;
import com.icbc.distributed.accdetails.entity.BizRegTableEntity;
import com.icbc.distributed.accdetails.service.BizRegTableService;


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