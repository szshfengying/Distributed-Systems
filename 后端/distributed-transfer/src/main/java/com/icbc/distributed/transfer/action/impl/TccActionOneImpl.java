package com.icbc.distributed.transfer.action.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icbc.distributed.transfer.entity.AccBalanceTableEntity;
import com.icbc.distributed.transfer.entity.TransferInfoEntity;
import com.icbc.distributed.transfer.action.ResultHolder;
import com.icbc.distributed.transfer.action.TccActionOne;
import com.icbc.distributed.transfer.tccservice.SeataTccTransferService;
import com.icbc.distributed.transfer.service.AccAgrtTableService;
import com.icbc.distributed.transfer.service.AccBalanceTableService;
import com.icbc.distributed.transfer.service.AccDetailsTableService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * The type Tcc action one.
 *
 * @author zhangsen
 */
@Service("tccActionOne")
public class TccActionOneImpl implements TccActionOne {

    @Autowired
    SeataTccTransferService seataTccTransferService;

    @Autowired
    private TransactionTemplate fromDsTransactionTemplate;

    @Autowired
    private AccBalanceTableService accBalanceTableService;

    @Autowired
    private AccAgrtTableService accAgrtTableService;

    @Autowired
    private AccDetailsTableService accDetailsTableService;


    @Override
    public boolean prepare(BusinessActionContext actionContext, TransferInfoEntity transferInfoEntity, String uuid, Double amount, AccBalanceTableEntity accBalanceTableEntityFrom) {
        String xid = actionContext.getXid();
        System.out.println("TccActionOne prepare, xid:" + xid);
        System.out.println("一阶段");

        return fromDsTransactionTemplate.execute(new TransactionCallback<Boolean>(){

//            @Transactional
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try {

                    System.out.println("deductbalance ... xid: " + RootContext.getXID());
                    RootContext.bind(RootContext.getXID());
                    System.out.println("付方一阶段执行");

                    QueryWrapper querywrapper = new QueryWrapper<AccBalanceTableEntity>();
                    querywrapper.eq("acc_id",accBalanceTableEntityFrom.getAccId());
                    AccBalanceTableEntity accBalanceTableEntity = accBalanceTableService.getOne(querywrapper);

//                    Double balance =  accBalanceTableEntityFrom.getCurBalance()-amount;
//                    accBalanceTableEntity.setCurBalance(balance);//更新余额
                    //                    System.out.println(balance);
//                    accBalanceTableEntity.setFreezeBalance(accBalanceTableEntity.getFreezeBalance()+amount);
                    Double frzeeBalance;
                    if(accBalanceTableEntity.getFreezeBalance()==null){
                         frzeeBalance = Double.valueOf(0);
                    }else {
                         frzeeBalance = accBalanceTableEntity.getFreezeBalance();
                    }
                    Double newFrzeeBalance = Double.valueOf(String.format("%17.2f", frzeeBalance+amount ));
                    Double newCurBalance = Double.valueOf(String.format("%17.2f", accBalanceTableEntity.getCurBalance()-amount));

                    accBalanceTableEntity.setFreezeBalance(newFrzeeBalance);
                    accBalanceTableEntity.setCurBalance(newCurBalance);
                    System.out.printf("现有余额%f,转账%f",accBalanceTableEntity.getCurBalance(),amount);
//                    accBalanceTableEntityFrom.setLastTxnDate(transferInfoEntity.getExecDate());//更新最后交易日
                    accBalanceTableEntity.setRegionId(null);
                    System.out.println();
                    System.out.println(accBalanceTableEntity);
                    accBalanceTableService.saveOrUpdate(accBalanceTableEntity);//插表
                    seataTccTransferService.addDetail(transferInfoEntity,uuid,amount,accBalanceTableEntity);
                    if("1900140000000490".equals(accBalanceTableEntity.getAccId())){
                        throw new RuntimeException("付方一阶段失败.");
                    }
//                    throw new RuntimeException("TccActionTwo failed.");
                    return true;
                } catch (Throwable t) {
                    t.printStackTrace();
                    return false;
                }
            }
        });
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        System.out.println("TccActionOne commit, xid:" + xid);
        System.out.println(actionContext.toString());

        Object object = actionContext.getActionContext("accBalanceTableEntityFrom");
        AccBalanceTableEntity accBalanceTableEntityFrom = JSON.parseObject(object.toString(), AccBalanceTableEntity.class);
        System.out.println(accBalanceTableEntityFrom.toString());
        BigDecimal bigDecimal = (BigDecimal)actionContext.getActionContext("amount");
        Double amount = bigDecimal.doubleValue();
        ResultHolder.setActionOneResult(xid, "T");
        return fromDsTransactionTemplate.execute(new TransactionCallback<Boolean>() {
//            @Transactional
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try{

                    System.out.println("deductbalance ... xid: " + RootContext.getXID());
                    RootContext.bind(RootContext.getXID());
                    System.out.println("付方一阶段提交");
                    QueryWrapper querywrapper = new QueryWrapper<AccBalanceTableEntity>();
                    querywrapper.eq("acc_id",accBalanceTableEntityFrom.getAccId());
                    AccBalanceTableEntity accBalanceTableEntity = accBalanceTableService.getOne(querywrapper);

                    Double frzeeBalance;
                    if(accBalanceTableEntity.getFreezeBalance()==null){
                        frzeeBalance = Double.valueOf(0);
                    }else {
                        frzeeBalance = accBalanceTableEntity.getFreezeBalance();
                    }
//                    System.out.println(accBalanceTableEntity);
//                    accBalanceTableEntity.setCurBalance(accBalanceTableEntity.getCurBalance()-amount);
                    Double newFrzeeBalance = Double.valueOf(String.format("%17.2f", frzeeBalance-amount ));

                    accBalanceTableEntity.setFreezeBalance(newFrzeeBalance);

                    accBalanceTableEntity.setLastTxnDate(accBalanceTableEntityFrom.getLastTxnDate());//更新最后交易日
                    accBalanceTableEntity.setRegionId(null);
//                    System.out.println(accBalanceTableEntity);
                    accBalanceTableService.saveOrUpdate(accBalanceTableEntity);//插表
                    System.out.println("付方一阶段提交成功");
                    if("3700120000000086".equals(accBalanceTableEntity.getAccId())){
                        throw new RuntimeException("付方二阶段提交失败");
                    }
//                    throw new RuntimeException("TccActionTwo failed.");
                    return true;
                }catch (Throwable t){
                    t.printStackTrace();
                    status.setRollbackOnly();
                    return false;
                }
            }
        });
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        System.out.println("TccActionOne rollback, xid:" + xid);
        System.out.println("付方一阶段开始回滚");
        System.out.println(actionContext.toString());
//        TransferInfoEntity transferInfoEntity = (TransferInfoEntity) actionContext.getActionContext("transferInfoEntity");
        String uuid = (String) actionContext.getActionContext("uuid");
        BigDecimal bigDecimal = (BigDecimal)actionContext.getActionContext("amount");
        Double amount = bigDecimal.doubleValue();
        Object object = actionContext.getActionContext("accBalanceTableEntityFrom");
        AccBalanceTableEntity accBalanceTableEntityFrom = JSON.parseObject(object.toString(), AccBalanceTableEntity.class);
        System.out.println(accBalanceTableEntityFrom.toString());
        ResultHolder.setActionOneResult(xid, "R");



        return fromDsTransactionTemplate.execute(new TransactionCallback<Boolean>() {

            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try{

                    System.out.println("deductbalance ... xid: " + RootContext.getXID());
                    RootContext.bind(RootContext.getXID());
//                    HashMap<String,Object> formmap = new HashMap<>();
//                    formmap.put("acc_id",accBalanceTableEntityFrom.getAccId());
//                    accDetailsTableService.listByMap(formmap);
                    QueryWrapper querywrapper = new QueryWrapper<AccBalanceTableEntity>();
                    querywrapper.eq("acc_id",accBalanceTableEntityFrom.getAccId());
                    AccBalanceTableEntity accBalanceTableEntity = accBalanceTableService.getOne(querywrapper);


//                    accBalanceTableEntity.setCurBalance(accBalanceTableEntity.getFreezeBalance()+accBalanceTableEntity.getCurBalance());//更新余额
                    Double frzeeBalance;
                    if(accBalanceTableEntity.getFreezeBalance()==null){
                        frzeeBalance = Double.valueOf(0);
                    }else {
                        frzeeBalance = accBalanceTableEntity.getFreezeBalance();
                    }
                    Double newFrzeeBalance = Double.valueOf(String.format("%17.2f", frzeeBalance-amount ));
                    Double newCurBalance = Double.valueOf(String.format("%17.2f", accBalanceTableEntity.getCurBalance()+amount ));

                    accBalanceTableEntity.setFreezeBalance(newFrzeeBalance);
                    accBalanceTableEntity.setCurBalance(newCurBalance);
                    accBalanceTableEntity.setLastTxnDate(accBalanceTableEntityFrom.getLastTxnDate());//更新最后交易日
                    accBalanceTableEntity.setRegionId(null);
                    System.out.println(accBalanceTableEntity);
                    accBalanceTableService.saveOrUpdate(accBalanceTableEntity);//插表

                    HashMap<String,Object> txnmap = new HashMap<>();
                    txnmap.put("txn_id",uuid);
                    accDetailsTableService.removeByMap(txnmap);
                    System.out.println("一阶段回滚成功");

//                    if(accBalanceTableEntity.getAccId().equals(4000000200000043L)){
//                        throw new RuntimeException("付方一阶段提交失败");
//                    }
                    return true;
                }catch (Throwable t){
                    t.printStackTrace();
                    status.setRollbackOnly();
                    return false;
                }
            }
        });
    }

}
