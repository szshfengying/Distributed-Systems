package com.icbc.distributed.accinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icbc.common.utils.PageUtils;
import com.icbc.distributed.accinfo.entity.BizRegTableEntity;

import java.util.Map;

/**
 * 
 *
 * @author ${author}
 * @email ${email}
 * @date 2020-07-20 20:31:15
 */
public interface BizRegTableService extends IService<BizRegTableEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

