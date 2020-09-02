package com.icbc.distributed.accopen.service;

import com.icbc.distributed.accopen.dao.AccIdTableDao;
import com.icbc.distributed.accopen.dao.AccInfoTableDao;
import com.icbc.distributed.accopen.entity.AccIdTableEntity;
import com.icbc.distributed.accopen.entity.AccInfoTableEntity;
import com.icbc.distributed.accopen.entity.ForgetEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ForgetService {
    @Autowired
    AccIdTableDao accIdTableDao;
    @Autowired
    AccInfoTableDao accInfoTableDao;
    public String set(ForgetEntity forgetEntity){
        Map map = new HashMap<String,String>();
        map.put("acc_id",forgetEntity.getAccId());
        //查询记录
        List<AccIdTableEntity> accIdTableEntities = accIdTableDao.selectByMap(map);
        if(accIdTableEntities.size()<1){
            return "";
        }
        AccIdTableEntity accIdTableEntity = accIdTableEntities.get(0);
        String md5 = DigestUtils.md5Hex(forgetEntity.getNewpassword()+forgetEntity.getAccId());
        //验证并修改
        if(accIdTableEntity.getNumber().equals(forgetEntity.getNumber()) && accIdTableEntity.getPhone().equals(forgetEntity.getPhone())){
            AccInfoTableEntity accInfoTableEntity = new AccInfoTableEntity();
            accInfoTableEntity.setAccId(forgetEntity.getAccId());
            accInfoTableEntity.setPassword(md5);
            accInfoTableDao.updateById(accInfoTableEntity);
            System.out.println(md5);
            return forgetEntity.getNewpassword();
        }else {
            return "";
        }
    }
}
