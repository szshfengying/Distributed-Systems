package com.icbc.distributed.transfer.tccservice;


import com.icbc.distributed.common.IdWorker;
import com.icbc.distributed.transfer.entity.*;
import com.icbc.distributed.transfer.service.*;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class SeataTccTransferService {

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




    //付款方减去余额
//    @GlobalTransactional
    @Transactional
    public void deductBalance(TransferInfoEntity transferInfoEntity, String uuid, Double amount, AccBalanceTableEntity accBalanceTableEntityFrom){

        System.out.println("deductbalance ... xid: " + RootContext.getXID());
        RootContext.bind(RootContext.getXID());


        accBalanceTableEntityFrom.setCurBalance(accBalanceTableEntityFrom.getCurBalance()-amount);//更新余额
        accBalanceTableEntityFrom.setLastTxnDate(transferInfoEntity.getExecDate());//更新最后交易日
        accBalanceTableEntityFrom.setRegionId(null);
        accBalanceTableService.saveOrUpdate(accBalanceTableEntityFrom);//插表
        addDetail(transferInfoEntity,uuid,amount,accBalanceTableEntityFrom);
//        throw new RuntimeException("xinyun exception");




    }

    //付款方减去余额回滚
    @Transactional
    public void rollBackdeductBalance(TransferInfoEntity transferInfoEntity,String uuid,Long amount,AccBalanceTableEntity accBalanceTableEntityFrom){

        System.out.println("deductbalance ... xid: " + RootContext.getXID());
        RootContext.bind(RootContext.getXID());


        accBalanceTableEntityFrom.setCurBalance(accBalanceTableEntityFrom.getCurBalance()-amount);//更新余额
        accBalanceTableEntityFrom.setLastTxnDate(accBalanceTableEntityFrom.getLastTxnDate());//更新最后交易日
        accBalanceTableEntityFrom.setRegionId(null);
        accBalanceTableService.saveOrUpdate(accBalanceTableEntityFrom);//插表

        HashMap<String,Object> map = new HashMap<>();
        map.put("txn_id",uuid);
        accBalanceTableService.removeByMap(map);
//        throw new RuntimeException("xinyun exception");
//        throw new RuntimeException("branch exception");
    }



    //收方增加余额
    //    @GlobalTransactional
    @Transactional
    public void addBalance(TransferInfoEntity transferInfoEntity,String uuid,Double amount,AccBalanceTableEntity accBalanceTableEntityTo) {
        System.out.println("addbalance ... xid: " + RootContext.getXID());

        RootContext.bind(RootContext.getXID());

        //业务
        accBalanceTableEntityTo.setCurBalance(accBalanceTableEntityTo.getCurBalance()+amount);//更新余额
        accBalanceTableEntityTo.setLastTxnDate(transferInfoEntity.getExecDate());//更新最后交易日
        accBalanceTableEntityTo.setRegionId(null);
        accBalanceTableService.saveOrUpdate(accBalanceTableEntityTo);//插表
        addDetail(transferInfoEntity,uuid,amount,accBalanceTableEntityTo);
        System.out.println("抛出异常");
        throw new RuntimeException("xinyun exception");

    }

    //收方增加余额回滚
    @Transactional
    public void rollBackaddBalance(TransferInfoEntity transferInfoEntity,String uuid,Long amount,AccBalanceTableEntity accBalanceTableEntityTo){

        System.out.println("addBalance ... xid: " + RootContext.getXID());
        RootContext.bind(RootContext.getXID());


        accBalanceTableEntityTo.setCurBalance(accBalanceTableEntityTo.getCurBalance()-amount);//更新余额
        accBalanceTableEntityTo.setLastTxnDate(accBalanceTableEntityTo.getLastTxnDate());//更新最后交易日
        accBalanceTableEntityTo.setRegionId(null);
        accBalanceTableService.saveOrUpdate(accBalanceTableEntityTo);//插表

        HashMap<String,Object> map = new HashMap<>();
        map.put("txn_id",uuid);
        accBalanceTableService.removeByMap(map);

//        throw new RuntimeException("xinyun exception");
//        throw new RuntimeException("branch exception");
    }




//    @GlobalTransactional
//    @Transactional
    public void addDetail(TransferInfoEntity transferInfoEntity, String uuid, Double amount, AccBalanceTableEntity accBalanceTableEntity){

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











}

