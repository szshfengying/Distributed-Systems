package com.icbc.distributed.accopen.service;

import com.icbc.distributed.accopen.dao.AccIdTableDao;
import com.icbc.distributed.accopen.dao.AccInfoTableDao;
import com.icbc.distributed.accopen.entity.AccIdTableEntity;
import com.icbc.distributed.accopen.entity.AccInfoTableEntity;
import com.icbc.distributed.accopen.entity.ResetEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResetService {

    @Autowired
    AccInfoTableDao accInfoTableDao;

    public String reset(ResetEntity resetEntity){
        Map map = new HashMap<String,String>();
        map.put("acc_id",resetEntity.getAccId());
        //查询记录
        List<AccInfoTableEntity> accInfoTableEntity = accInfoTableDao.selectByMap(map);
        if(accInfoTableEntity.size()<1){
            return "";
        }
        AccInfoTableEntity accInfoTableEntity1 = accInfoTableEntity.get(0);
        String md5 = DigestUtils.md5Hex(resetEntity.getNewpassword()+resetEntity.getAccId());
        //验证并修改
        if(accInfoTableEntity1.getPassword().equals(DigestUtils.md5Hex(resetEntity.getPassword()+resetEntity.getAccId()))){
            AccInfoTableEntity accInfoTableEntity2 = new AccInfoTableEntity();
            accInfoTableEntity2.setAccId(resetEntity.getAccId());
            accInfoTableEntity2.setPassword(md5);
            accInfoTableDao.updateById(accInfoTableEntity2);
            return resetEntity.getNewpassword();
        }else {
            return "";
        }
    }
}
