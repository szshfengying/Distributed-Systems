package com.icbc.distributed.accopen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icbc.distributed.accopen.dao.DetectLoginTableDao;
import com.icbc.distributed.accopen.entity.DetectLoginTableEntity;
import com.icbc.distributed.accopen.vo.request.AccLoginEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.List;


@Service
public class DetectLoginService {

    @Autowired
    DetectLoginTableDao detectLoginTableDao;



    public boolean detectLoginOk(String accId) {

        DetectLoginTableEntity detectLoginTableEntity = detectLoginTableDao.selectOne(new QueryWrapper<DetectLoginTableEntity>().eq("acc_id", accId));

        //没有登录信息
        if (detectLoginTableEntity == null) {
            return true;
        }


        int lockFlag = detectLoginTableEntity.getLockFlag();
        int failureNum = detectLoginTableEntity.getFailureNum();
        Date loginDate = detectLoginTableEntity.getLoginDate();

        if (lockFlag == 1) {
            // 用户被锁定
            if (localdateLtDate2(loginDate)) {
                // 锁定时间超过一天
                //删除用户的登录记录
                detectLoginTableDao.delete(new QueryWrapper<DetectLoginTableEntity>().eq("acc_id", accId));
            } else {
                // 锁定时间未满一天
                return false;
            }
        }

        return true;
    }


    /**
     * 判断当前时间与给定时间差是否大于5分钟
     *
     * @param date
     * @return 大于5分钟返回true
     * @throws Exception
     */
    public boolean localdateLtDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date date1;
        Date now;
        try {
            date1 = sdf.parse(sdf.format(date));

            now = sdf.parse(sdf.format(new Date()));
        }catch (ParseException e){
            System.out.println("时间解析错误");
            return false;
        }

        if (now.getTime() - date1.getTime() > 5 * 60 * 1000) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前时间与给定时间差是否大于一天
     *
     * @param date
     * @return 大于一天返回true
     * @throws Exception
     */
    public boolean localdateLtDate2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

        Date date1 = null;
        try {
            date1 = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("时间解析异常");
            return false;
        }
        Date now = null;
        try {
            now = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("时间解析异常");
            return false;
        }
        if (now.getTime() - date1.getTime() > 24 * 60 * 60 * 1000) {
            return true;
        } else {
            return false;
        }
    }




    public int registLoginError(String accid) {
        //登录失败要进行次数判断，还没被锁

        //根据用户名判断是否有过登录记录
        DetectLoginTableEntity detectLoginTableEntity = detectLoginTableDao.selectOne(new QueryWrapper<DetectLoginTableEntity>().eq("acc_id", accid));

        //没有登录失败记录，说明第一次登录失败
        if (detectLoginTableEntity == null) {
            detectLoginTableEntity = new DetectLoginTableEntity();
            detectLoginTableEntity.setAccId(accid);
            detectLoginTableEntity.setFailureNum(1);
            detectLoginTableEntity.setLockFlag(0);
            detectLoginTableEntity.setLoginDate(new Date());
            detectLoginTableDao.insert(detectLoginTableEntity);
            return 2;
        }

//        detectLoginTableEntityList.sort(new Comparator<DetectLoginTableEntity>() {
//            @Override
//            public int compare(DetectLoginTableEntity o1, DetectLoginTableEntity o2) {
////                return o1.getFailureNum() - o2.getFailureNum();
//                return (int) (o2.getLoginDate().getTime() - o1.getLoginDate().getTime());
//            }
//        });

        //有登录记录

        String accId = detectLoginTableEntity.getAccId();
        int lockFlag = detectLoginTableEntity.getLockFlag();
        int failureNum = detectLoginTableEntity.getFailureNum();
        Date loginDate = detectLoginTableEntity.getLoginDate();


        if (localdateLtDate(loginDate)) {
            //距离上次登录失败超过5分钟
            detectLoginTableDao.delete(new QueryWrapper<DetectLoginTableEntity>().eq("acc_id", accId));

            detectLoginTableEntity = new DetectLoginTableEntity();
            detectLoginTableEntity.setAccId(accId);
            detectLoginTableEntity.setFailureNum(1);
            detectLoginTableEntity.setLockFlag(0);
            detectLoginTableEntity.setLoginDate(new Date());
            detectLoginTableDao.insert(detectLoginTableEntity);

            return 2;
        } else {
            //未超过5分钟
            if (2==failureNum) {
                //上次登录失败时已错误两次
                detectLoginTableEntity.setFailureNum(3);
                detectLoginTableEntity.setLockFlag(1);
                detectLoginTableDao.update(detectLoginTableEntity, new QueryWrapper<DetectLoginTableEntity>().eq("acc_id", accId));
                return 0;
            } else {
                //上次登录失败时没超过两次

                detectLoginTableEntity.setAccId(accId);
                detectLoginTableEntity.setFailureNum(failureNum + 1);
                detectLoginTableEntity.setLockFlag(0);
                detectLoginTableEntity.setLoginDate(new Date());
                detectLoginTableDao.update(detectLoginTableEntity, new QueryWrapper<DetectLoginTableEntity>().eq("acc_id", accId));
                return 1;
            }

        }
    }

    public void registLoginSucces(String accId) {
        //登录成功要清除之前失败的记录
        detectLoginTableDao.delete(new QueryWrapper<DetectLoginTableEntity>().eq("acc_id", accId));
    }
}
