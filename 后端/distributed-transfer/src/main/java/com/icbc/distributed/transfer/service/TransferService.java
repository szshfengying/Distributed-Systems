package com.icbc.distributed.transfer.service;


import com.icbc.distributed.transfer.entity.*;
import com.icbc.distributed.transfer.feign.FClient;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import io.seata.spring.annotation.GlobalTransactional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

import com.icbc.distributed.common.IdWorker;


@Service
public class TransferService {

    @Autowired
    private AccBalanceTableService accBalanceTableService;

    @Autowired
    private AccAgrtTableService accAgrtTableService;

    @Autowired
    private AccDetailsTableService accDetailsTableService;

    @Autowired
    private BizRegTableService bizRegTableService;


    @Autowired
    private AccInfoTableService accInfoTableService;

    @Autowired
    IdWorker idWorker;
	
	@Autowired
    FClient fClient;


    //emplopyeeDao.insert(employee);
    //emplopyeeDao.updateById(employee);//根据id进行更新，没有传值的属性就不会更新
    //emplopyeeDao.updateAllColumnById(employee);//根据id进行更新，没传值的属性就更新为null
    //Employee employee = emplopyeeDao.selectById(1);
//    emplopyeeDao.selectOne(employeeCondition);
//    emplopyeeDao.deleteByMap(columnMap);
//    emplopyeeDao.deleteById(1);
    @GlobalTransactional
    public String transer(TransferInfoEntity transferInfoEntity) {

		String accFrom=transferInfoEntity.getAccIdFrom()+"";
        if(fClient.checkAcc(accFrom)){
            return "付方账号不存在或状态异常";
        }

        String accTo=transferInfoEntity.getAccIdTo()+"";
        if(fClient.checkAcc(accTo)){
            return "收方账号不存在或状态异常";
        }
	
        Map<String, Object> map = new HashMap<>();
        //kay是字段名 value是字段值
        map.put("acc_id", transferInfoEntity.getAccIdFrom());
//1数据初始化和检查
        Double amount = transferInfoEntity.getAmount();
//        System.out.println(transferInfoEntity);
//        transferInfoEntity.setTxnId(UUID.randomUUID().toString());
//2业务防重控制
        String uuid = "";
        while (true){
            uuid = UUID.randomUUID().toString().substring(0,30);
            Map<String, Object> bizmap = new HashMap<>();
            bizmap.put("txn_id",uuid);
            List<BizRegTableEntity> bizRegTableEntityList = bizRegTableService.listByMap(bizmap);
            System.out.printf("业务编号的个数为：%d\n",bizRegTableEntityList.size());
            System.out.println(bizRegTableEntityList.toString());
            if(bizRegTableEntityList.size()>0){
                System.out.println( "业务编号重复");
            }else if(bizRegTableEntityList.size() ==0){
                break;
            }

        }
//3新增业务登记簿
        BizRegTableEntity bizRegTableEntity = new BizRegTableEntity();
        bizRegTableEntity.setSnId(idWorker.nextId());//记录序号
        bizRegTableEntity.setTxnDate(transferInfoEntity.getExecDate());//交易日期
        bizRegTableEntity.setTxnId(uuid);//业务唯一编码
        bizRegTableEntity.setChannelType(1);//渠道种类
        bizRegTableEntity.setExecOrganno(transferInfoEntity.getExecOrganno());//操作机构
        bizRegTableEntity.setExecTellerno(transferInfoEntity.getExecTellerno());//操作柜员
        bizRegTableEntity.setTxnCode(111);//交易代码
        bizRegTableEntity.setTxnType(111);//交易类型
        bizRegTableEntity.setCashTransferFlag(1);//现金转账标志
        bizRegTableEntity.setDebitAccId(String.valueOf(transferInfoEntity.getAccIdFrom()));//借方账号
        bizRegTableEntity.setDebitAccName(transferInfoEntity.getAccTitleFrom());//借方名称
        bizRegTableEntity.setDebitCurrType(1);//借方币种 transferInfoEntity.getCurrType()
        bizRegTableEntity.setDebitAmount(amount);//借方发生额
        bizRegTableEntity.setCreditAccId(String.valueOf(transferInfoEntity.getAccIdTo()));//贷方账号
        bizRegTableEntity.setCreditAccName(transferInfoEntity.getAccTitleTo());//贷方币种
        bizRegTableEntity.setCreditCurrType(1);//贷方币种  transferInfoEntity.getCurrType()
        bizRegTableEntity.setCreditAmount(transferInfoEntity.getAmount());//贷方发生额
        bizRegTableEntity.setStatus(1);//状态1-处理中2-交易
        bizRegTableEntity.setLastModifyDate(transferInfoEntity.getExecDate());//最后更新日期
        bizRegTableEntity.setRegionId(1);//地区编号
        System.out.println(bizRegTableEntity.toString());
        bizRegTableService.save(bizRegTableEntity);

        //4查询账户信息表
        //判断借方账号是否存在
        List<AccInfoTableEntity> accInfoTableEntityFromList = accInfoTableService.listByMap(map);
        if(accInfoTableEntityFromList.size()<1){
            return "账号错误, 账号不存在";
        }

        //判断账号是否异常
        AccInfoTableEntity accInfoTableEntityFrom = accInfoTableEntityFromList.get(0);
        if(accInfoTableEntityFrom.getAccStatus()==-1){
            return "账号错误, 账号存在异常";
        }

        //判断收方账号是否存在
        map.put("acc_id", transferInfoEntity.getAccIdTo());
        List<AccInfoTableEntity> accInfoTableEntityToList = accInfoTableService.listByMap(map);

        if(accInfoTableEntityToList.size()<1){
            return "信息错误, 付方账号不存在";
        }
        //判断收方账号是否存在
        AccInfoTableEntity accInfoTableEntityTo = accInfoTableEntityToList.get(0);
        if(accInfoTableEntityTo.getAccStatus()==-1){
            return "账号错误,付方账号存在异常";
        }


        map.put("acc_id", transferInfoEntity.getAccIdFrom());
        List<AccBalanceTableEntity> accBalanceEntityListFrom = accBalanceTableService.listByMap(map);

//        System.out.println(accBalanceEntityListFrom.size());
        if(accBalanceEntityListFrom.size()==0){
            return "账户余额信息缺失";
        }else if(accBalanceEntityListFrom.size()>1){
            return "余额信息重复";
        }

        map.put("acc_id", transferInfoEntity.getAccIdTo());
        List<AccBalanceTableEntity> accBalanceEntityListTo = accBalanceTableService.listByMap(map);

        if(accBalanceEntityListTo.size()==0){
            return "收方账户余额信息缺失";
        }else if(accBalanceEntityListTo.size()>1){
            return "收方余额重复";
        }

//

//5查询账户余额表
//6账户余额更新
//7新增双方账户明细表

        AccBalanceTableEntity accBalanceTableEntityFrom = accBalanceEntityListFrom.get(0);
        if (accBalanceTableEntityFrom.getCurBalance() < amount || accBalanceTableEntityFrom.getCurBalance()<0) {
            return "余额不足";
        }

        //减去余额
        //增加发起方明细
        System.out.println("开始扣款");
        deductBalance(transferInfoEntity,uuid,amount,accBalanceTableEntityFrom);//插表
//

        //增加余额
        //增加收方明细
        System.out.println("开始收款账");
        AccBalanceTableEntity accBalanceTableEntityTo = accBalanceEntityListTo.get(0);
        addBalance(transferInfoEntity,uuid,amount,accBalanceTableEntityTo);//插表


        return "转账成功";

    }

    //减去余额
//    @GlobalTransactional
    @Transactional
    public void deductBalance(TransferInfoEntity transferInfoEntity,String uuid,Double amount,AccBalanceTableEntity accBalanceTableEntityFrom){

        System.out.println("deductbalance ... xid: " + RootContext.getXID());
        RootContext.bind(RootContext.getXID());


        accBalanceTableEntityFrom.setCurBalance(accBalanceTableEntityFrom.getCurBalance()-amount);//更新余额
        accBalanceTableEntityFrom.setLastTxnDate(transferInfoEntity.getExecDate());//更新最后交易日
        accBalanceTableEntityFrom.setRegionId(null);
        accBalanceTableService.saveOrUpdate(accBalanceTableEntityFrom);//插表
        addDetail(transferInfoEntity,uuid,amount,accBalanceTableEntityFrom);
//        throw new RuntimeException("xinyun exception");


//        throw new RuntimeException("branch exception");
    }


    @Transactional
    public void rollBackdeductBalance(TransferInfoEntity transferInfoEntity,String uuid,Double amount,AccBalanceTableEntity accBalanceTableEntityFrom){

        System.out.println("deductbalance ... xid: " + RootContext.getXID());
        RootContext.bind(RootContext.getXID());


        accBalanceTableEntityFrom.setCurBalance(accBalanceTableEntityFrom.getCurBalance()-amount);//更新余额
        accBalanceTableEntityFrom.setLastTxnDate(transferInfoEntity.getExecDate());//更新最后交易日
        accBalanceTableEntityFrom.setRegionId(null);
        accBalanceTableService.saveOrUpdate(accBalanceTableEntityFrom);//插表
        addDetail(transferInfoEntity,uuid,amount,accBalanceTableEntityFrom);
//        throw new RuntimeException("xinyun exception");


//        throw new RuntimeException("branch exception");
    }



//增加余额
//    @GlobalTransactional
//    @Transactional
    public void addBalance(TransferInfoEntity transferInfoEntity, String uuid, Double amount, AccBalanceTableEntity accBalanceTableEntityTo) {
        System.out.println("addbalance ... xid: " + RootContext.getXID());

        RootContext.bind(RootContext.getXID());

        //业务
        accBalanceTableEntityTo.setCurBalance(accBalanceTableEntityTo.getCurBalance()+amount);//更新余额
        accBalanceTableEntityTo.setLastTxnDate(transferInfoEntity.getExecDate());//更新最后交易日
        accBalanceTableEntityTo.setRegionId(null);
        accBalanceTableService.saveOrUpdate(accBalanceTableEntityTo);//插表
        addDetail(transferInfoEntity,uuid,amount,accBalanceTableEntityTo);
        throw new RuntimeException("some exception");
    }

//    @GlobalTransactional
//    @Transactional
    public void addDetail(TransferInfoEntity transferInfoEntity,String uuid,Double amount,AccBalanceTableEntity accBalanceTableEntity){

        System.out.println("addbalance ... xid: " + RootContext.getXID());
        RootContext.bind(RootContext.getXID());

        AccDetailsTableEntity accDetailsTableEntity = new AccDetailsTableEntity();
        accDetailsTableEntity.setSnId(idWorker.nextId());//明细序号
        accDetailsTableEntity.setRegionId(1);//分区编号
        accDetailsTableEntity.setAccId(transferInfoEntity.getAccIdFrom());//账号
        accDetailsTableEntity.setCurrType(accBalanceTableEntity.getCurrType());//币种
        accDetailsTableEntity.setTxnId(uuid);//业务唯一编码
        accDetailsTableEntity.setExecOrganno(transferInfoEntity.getExecOrganno());//执行机构
        accDetailsTableEntity.setExecTellerno(transferInfoEntity.getExecTellerno());//执行柜员
        accDetailsTableEntity.setExecDate(transferInfoEntity.getExecDate());//交易日期
        accDetailsTableEntity.setTxnCode(1);//交易代码
        accDetailsTableEntity.setCashFlag(1);//现金标志
        accDetailsTableEntity.setAmount(amount);//发生额
        accDetailsTableEntity.setBalance(accBalanceTableEntity.getCurBalance());//余额
        accDetailsTableEntity.setDepartId(11);//地区编号
        accDetailsTableEntity.setLoanFlag(1);//借贷标志
        accDetailsTableService.save(accDetailsTableEntity);
    }



//    @TwoPhaseBusinessAction(name = "tccActionForSpringTest" , commitMethod = "commit", rollbackMethod = "rollback")
//    public boolean prepare(BusinessActionContext actionContext, int i){return true;}
//    public boolean commit(BusinessActionContext actionContext){return true;}
//    public boolean rollback(BusinessActionContext actionContext){return  true;}


    @Test
    public void test() throws TxnIdRepeatException {
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        System.out.println(uuid.substring(0,30));
        IdWorker idGen = new IdWorker();
        System.out.println(idGen.nextId());
        Date date=new Date();
        Calendar cal=Calendar.getInstance();
        System.out.println(cal);
        HashMap map = new HashMap();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(df.format(date));



    }




}

class TxnIdRepeatException extends Exception{
    public TxnIdRepeatException() {
        super("业务号重复");
    }
}

class AccInfoNotFoundException extends Exception{
    public AccInfoNotFoundException() {
        super("没有账号");
    }
}

class AmountNotEnoughException extends Exception{
    public AmountNotEnoughException() {
        super("余额不够");
    }
}