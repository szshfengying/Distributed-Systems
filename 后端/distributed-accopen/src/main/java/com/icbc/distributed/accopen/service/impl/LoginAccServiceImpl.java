package com.icbc.distributed.accopen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icbc.common.exception.MsgCode.BaseCode;
import com.icbc.common.exception.RRException;
import com.icbc.distributed.accopen.dao.AccInfoTableDao;
import com.icbc.distributed.accopen.entity.AccInfoTableEntity;
import com.icbc.distributed.accopen.service.DetectLoginService;
import com.icbc.distributed.accopen.service.LoginAccService;
import com.icbc.distributed.accopen.vo.request.AccLoginEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginAccServiceImpl implements LoginAccService {
    @Autowired
    AccInfoTableDao accInfoTableDao;
    @Autowired
    DetectLoginService detectLoginService;
    @Override
    public String loginAcc(AccLoginEntity accLoginEntity){
        String accid=accLoginEntity.getAccid();
        String password= DigestUtils.md5Hex(accLoginEntity.getPassword() + accid);
        //1.去数据库查询
        AccInfoTableEntity entity=accInfoTableDao.selectOne(new QueryWrapper<AccInfoTableEntity>()
                .eq("acc_id",accid));
        if (entity==null){
            //没有该账号
//            throw new RRException(BaseCode.LOGIN_ACCID_INVAILD_EXCEPTION);
            return "账号不存在";
        } else {

            //cjy 账号正确，开始判断账号登录次数
            if(!detectLoginService.detectLoginOk(accid)){
                return "账号已被锁定，请24小时后重新尝试";
            }

//            detectLoginService.registLogin( accid, password);

            entity=accInfoTableDao.selectOne(new QueryWrapper<AccInfoTableEntity>()
                    .eq("acc_id",accid)
                    .eq("password",password));
            if(entity==null){
                //密码错误
                //登录失败记录
                int fail = detectLoginService.registLoginError(accid);
                return "登录失败，登录失败将锁定账号24小时，剩余"+String.valueOf(fail)+"次。";
//                throw new RRException(BaseCode.LOGIN_PASSWORD_INVAILD_EXCEPTION);
            }else{
                //成功登录记
                detectLoginService.registLoginSucces(accid);
                return "登录成功";
            }

        }
    }
}