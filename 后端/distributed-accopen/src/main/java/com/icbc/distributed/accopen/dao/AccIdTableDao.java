package com.icbc.distributed.accopen.dao;

import com.icbc.distributed.accopen.entity.AccIdTableEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2020-07-20 20:37:46
 */

@Mapper
public interface AccIdTableDao extends BaseMapper<AccIdTableEntity> {

    Integer insertAndReturnId(AccIdTableEntity accIdTableEntity);
}
