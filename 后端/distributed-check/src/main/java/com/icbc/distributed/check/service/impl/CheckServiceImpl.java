package com.icbc.distributed.check.service.impl;

import com.alibaba.fastjson.JSON;
import com.icbc.distributed.check.dao.AccBalanceTableDao;
import com.icbc.distributed.check.dao.AccDetailsTableDao;
import com.icbc.distributed.check.entity.NettingEntity;
import com.icbc.distributed.check.service.CheckService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CheckServiceImpl implements CheckService {

    List<NettingEntity> resultEntities = new ArrayList<>();

    @Autowired
    AccBalanceTableDao accBalanceTableDao;
    @Autowired
    AccDetailsTableDao accDetailsTableDao;

    @Override
    public List<NettingEntity> CheckResult() {
        if(resultEntities.size()==0)
            System.out.println("当天对账结束，对账成功");
        else
            System.out.println("登记对账错误谱");

        for(NettingEntity nettingEntity: resultEntities){
            System.out.print(" 账号："+nettingEntity.getAccId());
            System.out.print(" 不平金额："+nettingEntity.getResult());
            System.out.print(" 借贷类型："+nettingEntity.getLoanFlag());
            System.out.print(" 发生额："+nettingEntity.getAmount());
            System.out.println(" 交易日期："+nettingEntity.getExecDate());
        }
        return resultEntities;
    }
    @Transactional
//    @Scheduled(cron="0 0 0 * * ?")
    @Scheduled(cron="0 0/1 19 * * ?")
    public void AutoCheck() {

        //判断总分平不平
        try{
            int nettingSum = 0;
            List<NettingEntity> nettingEntities = accDetailsTableDao.netting();
            FileOutputStream fos = new FileOutputStream("G://autoCheckFile.txt",true);
            String tmp = "";
            Date date = new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
            for (NettingEntity netting : nettingEntities) {
                if(netting.getResult()!=0){
                    resultEntities.add(netting);
                    tmp = " 账号："+netting.getAccId() + "\t" +
                            " 不平金额："+netting.getResult() + "\t" +
                            " 借贷类型："+netting.getLoanFlag() + "\t" +
                            " 发生额："+netting.getAmount() + "\t" +
                            " 交易日期："+netting.getExecDate() + "\n";
                    fos.write(tmp.getBytes());
                }
                else nettingSum++;
            }
            if(nettingSum==nettingEntities.size()){   //如果对账平的账户数和账户总数相等，说明总分平，对账成功
                System.out.println("当天对账结束，对账成功");
                fos.write(("--------------------------- 对账时间：" + dateFormat.format(date) + ", 对账成功！------------------------------\n").getBytes());
            }
            else {
                System.out.println("登记对账错误谱");
                fos.write(("--------------------------- 对账时间：" + dateFormat.format(date) + ", 对账存在错误！------------------------------\n").getBytes());
            }


            fos.flush();
        }catch (Exception e){
            System.out.println(e);
        }

    }


}
