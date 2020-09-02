package com.icbc.distributed.transfer.action.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icbc.distributed.transfer.entity.AccBalanceTableEntity;
import com.icbc.distributed.transfer.entity.TransferInfoEntity;
import com.icbc.distributed.transfer.action.ResultHolder;
import com.icbc.distributed.transfer.action.TccActionTwo;
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
 * The type Tcc action two.
 *
 * @author zhangsen
 */
@Service("tccActionTwo")
public class TccActionTwoImpl implements TccActionTwo {

    @Autowired
    SeataTccTransferService seataTccTransferService;
    @Autowired
    private TransactionTemplate toDsTransactionTemplate;


    @Autowired
    private AccBalanceTableService accBalanceTableService;

    @Autowired
    private AccAgrtTableService accAgrtTableService;

    @Autowired
    private AccDetailsTableService accDetailsTableService;


    @Override
    public boolean prepare(BusinessActionContext actionContext, TransferInfoEntity transferInfoEntity, String uuid, Double amount, AccBalanceTableEntity accBalanceTableEntityTo) {
        String xid = actionContext.getXid();
        System.out.println("TccActionTwo prepare, xid:" + xid);


        return toDsTransactionTemplate.execute(new TransactionCallback<Boolean>() {
//            @Transactional
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try{

                    System.out.println("addbalance ... xid: " + RootContext.getXID());
                    System.out.println("收方开始执行阶段");
                    RootContext.bind(RootContext.getXID());

                    //业务
//                    accBalanceTableEntityTo.setCurBalance(accBalanceTableEntityTo.getCurBalance()+amount);//更新余额
//                    accBalanceTableEntityTo.setLastTxnDate(transferInfoEntity.getExecDate());//更新最后交易日
                    QueryWrapper querywrapper = new QueryWrapper<AccBalanceTableEntity>();
                    querywrapper.eq("acc_id",accBalanceTableEntityTo.getAccId());
                    AccBalanceTableEntity accBalanceTableEntity = accBalanceTableService.getOne(querywrapper);

                    Double frzeeBalance;
                    if(accBalanceTableEntity.getFreezeBalance()==null){
                        frzeeBalance = Double.valueOf(0);
                    }else {
                        frzeeBalance = accBalanceTableEntity.getFreezeBalance();
                    }
                    Double newFrzeeBalance = Double.valueOf(String.format("%17.2f", frzeeBalance+amount ));


                    accBalanceTableEntity.setFreezeBalance(newFrzeeBalance);
                    accBalanceTableEntity.setRegionId(null);
                    accBalanceTableService.saveOrUpdate(accBalanceTableEntity);//插表
                    seataTccTransferService.addDetail(transferInfoEntity,uuid,amount,accBalanceTableEntityTo);
                    if("2000100000000517".equals(accBalanceTableEntity.getAccId())){
                        throw new RuntimeException("二阶段执行失败");
                    }
//                    System.out.println(String.format("Undo prepareAdd account[%s] amount[%f], dtx transaction id: %s.", accountNo, amount, xid));
                    return true;
                }catch (Throwable t){
                    t.printStackTrace();
                    return false;
                }
            }
        });
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        System.out.println("TccActionTwo commit, xid:" + xid);
        System.out.println(actionContext.toString());
        BigDecimal bigDecimal = (BigDecimal)actionContext.getActionContext("amount");
        Double amount = bigDecimal.doubleValue();
        Object object = actionContext.getActionContext("accBalanceTableEntityTo");
        AccBalanceTableEntity accBalanceTableEntityTo = JSON.parseObject(object.toString(), AccBalanceTableEntity.class);
        ResultHolder.setActionTwoResult(xid, "T");

        return toDsTransactionTemplate.execute(new TransactionCallback<Boolean>() {
//            @Transactional
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try{

                    RootContext.bind(actionContext.getXid());//RootContext.getXID()
                    System.out.println("deductbalance ... xid: " + RootContext.getXID());
                    System.out.println("收方二阶段提交");

                    QueryWrapper querywrapper = new QueryWrapper<AccBalanceTableEntity>();
                    querywrapper.eq("acc_id",accBalanceTableEntityTo.getAccId());
                    AccBalanceTableEntity accBalanceTableEntity = accBalanceTableService.getOne(querywrapper);

                    Double frzeeBalance;
                    if(accBalanceTableEntity.getFreezeBalance()==null){
                        frzeeBalance = Double.valueOf(0);
                    }else {
                        frzeeBalance = accBalanceTableEntity.getFreezeBalance();
                    }

                    Double newFrzeeBalance = Double.valueOf(String.format("%17.2f", frzeeBalance - amount ));
                    Double newCurBalance = Double.valueOf(String.format("%17.2f", accBalanceTableEntity.getCurBalance() + amount ));


                    accBalanceTableEntity.setCurBalance(newCurBalance);//更新余额
                    accBalanceTableEntity.setFreezeBalance(newFrzeeBalance);//更新余额
                    accBalanceTableEntity.setLastTxnDate(accBalanceTableEntityTo.getLastTxnDate());//更新最后交易日
                    accBalanceTableEntity.setRegionId(null);
                    accBalanceTableService.saveOrUpdate(accBalanceTableEntity);//插表
//                    if("52001400000005300".equals(accBalanceTableEntity.getAccId())){
//                        throw new RuntimeException("二阶段提交失败");
//                    }
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
        System.out.println("TccActionTwo rollback, xid:" + xid);
        ResultHolder.setActionTwoResult(xid, "R");

        System.out.println("收方二阶段开始回滚");
        System.out.println(actionContext.toString());
//        TransferInfoEntity transferInfoEntity = (TransferInfoEntity) actionContext.getActionContext("transferInfoEntity");
        String uuid = (String) actionContext.getActionContext("uuid");
        System.out.println(uuid);
        BigDecimal bigDecimal = (BigDecimal)actionContext.getActionContext("amount");
        Double amount = bigDecimal.doubleValue();
        Object object = actionContext.getActionContext("accBalanceTableEntityTo");
        AccBalanceTableEntity accBalanceTableEntityTo = JSON.parseObject(object.toString(), AccBalanceTableEntity.class);

        System.out.println(accBalanceTableEntityTo.toString());

        return  toDsTransactionTemplate.execute(new TransactionCallback<Boolean>() {

            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try{
                    System.out.println("deductbalance ... xid: " + RootContext.getXID());
                    RootContext.bind(RootContext.getXID());

                    QueryWrapper querywrapper = new QueryWrapper<AccBalanceTableEntity>();
                    querywrapper.eq("acc_id",accBalanceTableEntityTo.getAccId());
                    AccBalanceTableEntity accBalanceTableEntity = accBalanceTableService.getOne(querywrapper);
                    Double frzeeBalance;
                    if(accBalanceTableEntity.getFreezeBalance()==null){
                        frzeeBalance = Double.valueOf(0);
                    }else {
                        frzeeBalance = accBalanceTableEntity.getFreezeBalance();
                    }

                    Double newFrzeeBalance = Double.valueOf(String.format("%17.2f", frzeeBalance-amount ));


                    accBalanceTableEntity.setFreezeBalance(newFrzeeBalance);
                    accBalanceTableEntity.setLastTxnDate(accBalanceTableEntityTo.getLastTxnDate());//更新最后交易日
                    accBalanceTableEntity.setRegionId(null);
                    System.out.println(accBalanceTableEntity);
                    accBalanceTableService.saveOrUpdate(accBalanceTableEntity);//插表

                    HashMap<String,Object> txnmap = new HashMap<>();
                    txnmap.put("txn_id",uuid);
                    accDetailsTableService.removeByMap(txnmap);
                    System.out.println("二阶段回滚成功");
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
