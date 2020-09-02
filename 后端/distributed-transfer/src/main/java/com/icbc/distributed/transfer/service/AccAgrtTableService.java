package com.icbc.distributed.transfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icbc.common.utils.PageUtils;
import com.icbc.distributed.transfer.entity.AccAgrtTableEntity;

import java.util.Map;

/**
 * 
 *
 * @author ${author}
 * @email ${email}
 * @date 2020-07-20 20:46:50
 */
public interface AccAgrtTableService extends IService<AccAgrtTableEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

