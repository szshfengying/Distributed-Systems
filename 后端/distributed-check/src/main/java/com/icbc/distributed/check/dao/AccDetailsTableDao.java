package com.icbc.distributed.check.dao;

import com.icbc.distributed.check.entity.AccDetailsTableEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.distributed.check.entity.NettingEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2020-07-20 20:40:33
 */
@Mapper
public interface AccDetailsTableDao extends BaseMapper<AccDetailsTableEntity> {
	List<NettingEntity> netting();
}
