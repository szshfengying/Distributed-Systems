package com.icbc.distributed.interest.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import  com.icbc.distributed.common.IdWorker;
import com.icbc.distributed.interest.entity.AccAgrtTableEntity;
import com.icbc.distributed.interest.entity.AccBalanceTableEntity;
import com.icbc.distributed.interest.entity.AccDetailsTableEntity;
import com.icbc.distributed.interest.entity.AccIntTableEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Service
public class InterestComputer implements Job {
    @Autowired
    private AccDetailsTableService accDetailsTableService;

    @Autowired
    private AccBalanceTableService accBalanceTableService;

    @Autowired
    private AccIntTableService accIntTableService;

    @Autowired
    private AccAgrtTableService accAgrtTableService;

    @Autowired
    IdWorker idWorker;

    @Override
    @Transactional
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        compute();
    }

    @Test
    @Transactional
    public void compute(){

//        queryWrapper.eq("acc_id","2600000300000099");

        // 1.每天零点1分开始自动进行计息处理
        System.out.println("开始计息");

        // 2.获取系统日期作为交易日期
        QueryWrapper<AccDetailsTableEntity> queryWrapper = new QueryWrapper<AccDetailsTableEntity>();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DATE,-1);
        String yesterdayDate=dateFormat.format(calendar.getTime());
        System.out.println(yesterdayDate);
        queryWrapper.eq("exec_date",yesterdayDate);


        // 3.对账户明细表按照账号和日期排序
        queryWrapper.orderByAsc("acc_id","exec_date");

//        queryWrapper.eq( "acc_id",date);
//        List<AccDetailsTableEntity> page = accDetailsTableService.listByMap(map);
        List<AccDetailsTableEntity> page = accDetailsTableService.list(queryWrapper);
        List<AccDetailsTableEntity> accDetailList = new ArrayList<AccDetailsTableEntity>();

        AccDetailsTableEntity change = new AccDetailsTableEntity();
        if(page.size()>0){
            change = page.get(0);
        }
        for (AccDetailsTableEntity acc:page) {
            if(acc.getAccId().equals(change.getAccId())){
                accDetailList.add(acc);
                continue;
            }else {
                change = acc;
                accDetailList.sort((AccDetailsTableEntity o1, AccDetailsTableEntity o2) -> {
                    return (int) (o1.getSnId() - o2.getSnId());
                });
            }

            compute45678(accDetailList.get(accDetailList.size()-1));
            System.out.println(accDetailList.toString());
        }
        System.out.println(page);
        System.out.println("结束计息");


    }

    public void compute45678(AccDetailsTableEntity accDetailsTableEntity){
        // 4.按照账户进行计息处理
        float interest = accDetailsTableEntity.getAmount() * 0.0001f;
        AccAgrtTableEntity accAgrtTableEntity = new AccAgrtTableEntity();//计息协议

        // 5.更新账户余额
        updateAccBalance(accDetailsTableEntity.getAccId(),new Date(),interest);
        // 6.登记账号计息明细表
        registerAccInterest(accAgrtTableEntity, (long) interest,new Date(),accDetailsTableEntity);
        // 7.登记账户明细表
        registerAccDetails(accDetailsTableEntity, (long) interest,accDetailsTableEntity.getBalance(),new Date());

        //8.更新存款协议表
        updateAccAgrt(accDetailsTableEntity.getAccId());
    }

    // 5.更新账户余额
    public void updateAccBalance(long accId,Date date,float interest){
        HashMap<String,Object> columnMap = new HashMap<>();
        columnMap.put("acc_id",accId);
        List<AccBalanceTableEntity> accBalanceTableEntityList = accBalanceTableService.listByMap(columnMap);
        if(accBalanceTableEntityList.size() != 1){
            return;
        }
        AccBalanceTableEntity accBalanceTableEntity = accBalanceTableEntityList.get(0);
        accBalanceTableEntity.setAccId(accId);
        accBalanceTableEntity.setCurBalance((long) (interest+accBalanceTableEntity.getCurBalance()));
//        accBalanceTableEntity.setCurrType(accBalanceTableEntity.getCurrType());
        accBalanceTableEntity.setLastTxnDate(date);
        accBalanceTableEntity.setRegionId(null);
//        accBalanceTableEntity.setSnId(idWorker.nextId());
        accBalanceTableEntity.setYdayBalance(accBalanceTableEntity.getCurBalance());
        accBalanceTableService.saveOrUpdate(accBalanceTableEntity);
    }


    // 6.登记账号计息明细表
    public void registerAccInterest(AccAgrtTableEntity accAgrtTableEntity,long interest,Date date,AccDetailsTableEntity accDetails){
        AccIntTableEntity accIntTableEntity = new AccIntTableEntity();
        accIntTableEntity.setSnId(idWorker.nextId());//行号
        accIntTableEntity.setAccId(accDetails.getAccId());//账号 accDetails.getAccId()
        accIntTableEntity.setCurrType(1);//币种 accDetails.getCurrType()
        accIntTableEntity.setIntEvino("12345678");//结息证据号
        accIntTableEntity.setStatus(1);//状态

//        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DATE,-1);

        accIntTableEntity.setBeginDate(calendar.getTime());//计息开始日
        accIntTableEntity.setEndDate(calendar.getTime());//计息结束日

        accIntTableEntity.setIntDate(1);//计息天数
        accIntTableEntity.setAmount(12L);//金额
        accIntTableEntity.setRateIncn(1);//利率变动方式
        accIntTableEntity.setRateAltf(1);//利率变动周期
        accIntTableEntity.setRateCode(1);//利率代码
        accIntTableEntity.setBaseRate(1);//基准利率
        accIntTableEntity.setFloaType(1);//浮动方式
        accIntTableEntity.setFloaRate(1);//浮动率
        accIntTableEntity.setExecRate(1);//执行利率
        accIntTableEntity.setExecOrganno("1");//执行机构
        accIntTableEntity.setExecTellerno("1");//执行柜员
        accIntTableEntity.setExecDate(new Date());//执行日期
        accIntTableEntity.setExecTimestamp(new Date());//执行时间
        accIntTableEntity.setRegionId(1);//地区号
        accIntTableService.saveOrUpdate(accIntTableEntity);
    }
    // 7.登记账户明细表
    public void registerAccDetails(AccDetailsTableEntity accDetails,long interest,long balance,Date date){
        AccDetailsTableEntity accDetailsTableEntity = new AccDetailsTableEntity();
        accDetailsTableEntity.setAccId(accDetails.getAccId());
        accDetailsTableEntity.setAmount(interest);
        accDetailsTableEntity.setBalance(balance);
        accDetailsTableEntity.setCashFlag(accDetails.getCashFlag());
        accDetailsTableEntity.setCurrType(accDetails.getCurrType());
        accDetailsTableEntity.setDepartId(accDetails.getDepartId());
        accDetailsTableEntity.setExecDate(date);
        accDetailsTableEntity.setExecOrganno(accDetails.getExecOrganno());
        accDetailsTableEntity.setExecTellerno(accDetails.getExecTellerno());
        accDetailsTableEntity.setLoanFlag(accDetails.getLoanFlag());
        accDetailsTableEntity.setRegionId(accDetails.getRegionId());
        accDetailsTableEntity.setSnId(idWorker.nextId());
        accDetailsTableEntity.setTxnCode(accDetails.getTxnCode());
        accDetailsTableEntity.setTxnId(accDetails.getTxnId());
        accDetailsTableService.save(accDetailsTableEntity);
    }

    //8.更新存款协议表
    public void updateAccAgrt(long acc){

        HashMap<String,Object> cloumnMap = new HashMap<>();
        cloumnMap.put("acc_id",acc);

        List<AccAgrtTableEntity> AgrtList= accAgrtTableService.listByMap(cloumnMap);
        if(AgrtList.size() != 1){
            return;//抛出异常  抛出异常 保证后续  继续继续运行
        }

        AccAgrtTableEntity accAgrtTableEntity = AgrtList.get(0);
        accAgrtTableEntity.setAccId(acc);

//        accAgrtTableEntity.set
        accAgrtTableService.saveOrUpdate(accAgrtTableEntity);
    }



}
