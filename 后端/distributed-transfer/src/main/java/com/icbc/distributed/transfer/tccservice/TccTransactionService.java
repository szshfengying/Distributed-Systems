package com.icbc.distributed.transfer.tccservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icbc.common.utils.R;
import com.icbc.distributed.common.IdWorker;
import com.icbc.distributed.transfer.entity.AccBalanceTableEntity;
import com.icbc.distributed.transfer.entity.AccInfoTableEntity;
import com.icbc.distributed.transfer.entity.BizRegTableEntity;
import com.icbc.distributed.transfer.entity.TransferInfoEntity;
import com.icbc.distributed.transfer.action.TccActionOne;
import com.icbc.distributed.transfer.action.TccActionTwo;
import com.icbc.distributed.transfer.service.*;



import io.seata.spring.annotation.GlobalTransactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The type Tcc transaction service.
 *
 * @author zhangsen
 */

@Service("tccTransaction")
public class TccTransactionService {

    @Autowired
    private TccActionOne tccActionOne;


    @Autowired
    private TccActionTwo tccActionTwo;

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


//    @GlobalTransactional
//    public String tansfer(TransferInfoEntity transferInfoEntity){
//        String ret = "转账失败";
//        try {
//            ret = doTransactionCommit(transferInfoEntity);
//        }catch (Throwable t){
//            t.printStackTrace();
//            return  "转账失败";
//        }
//        return ret;
//    }


    public R checkAcc(TransferInfoEntity transferInfoEntity){

        Map<String, Object> map = new HashMap<>();
        //kay是字段名 value是字段值
        map.put("acc_id", transferInfoEntity.getAccIdFrom());
        //1数据初始化和检查
        Double amount = transferInfoEntity.getAmount();

        //4查询账户信息表
        //判断借方账号是否存在
        List<AccInfoTableEntity> accInfoTableEntityFromList = accInfoTableService.listByMap(map);
        if(accInfoTableEntityFromList.size()<1){

            return R.error("账号错误, 账号不存在");
        }

        //判断账号是否异常
        AccInfoTableEntity accInfoTableEntityFrom = accInfoTableEntityFromList.get(0);
        if(!accInfoTableEntityFrom.getAccTitle().equals(transferInfoEntity.getAccTitleFrom())){
            return  R.error("付方账号与用户名对不上");
        }

        if(accInfoTableEntityFrom.getAccStatus()==-1){
            return  R.error("账号错误, 账号存在异常");
        }

        //判断收方账号是否存在
        map.put("acc_id", transferInfoEntity.getAccIdTo());
        List<AccInfoTableEntity> accInfoTableEntityToList = accInfoTableService.listByMap(map);

        if(accInfoTableEntityToList.size()<1){
            return  R.error("信息错误, 付方账号不存在");
        }


        //判断收方账号是否存在异常
        AccInfoTableEntity accInfoTableEntityTo = accInfoTableEntityToList.get(0);
        if(!accInfoTableEntityTo.getAccTitle().equals(transferInfoEntity.getAccTitleTo())){
            return  R.error("收方账号与用户名对不上");
        }

        if(accInfoTableEntityTo.getAccStatus()==-1){
            return  R.error("账号错误,付方账号存在异常");
        }


        map.put("acc_id", transferInfoEntity.getAccIdFrom());
        List<AccBalanceTableEntity> accBalanceEntityListFrom = accBalanceTableService.listByMap(map);

//        System.out.println(accBalanceEntityListFrom.size());
        if(accBalanceEntityListFrom.size()==0){
            return  R.error("账户余额信息缺失");
        }else if(accBalanceEntityListFrom.size()>1){
            return  R.error("余额信息重复");
        }

        map.put("acc_id", transferInfoEntity.getAccIdTo());
        List<AccBalanceTableEntity> accBalanceEntityListTo = accBalanceTableService.listByMap(map);

        if(accBalanceEntityListTo.size()==0){
            return  R.error("收方账户余额信息缺失");
        }else if(accBalanceEntityListTo.size()>1){
            return  R.error("收方余额重复");
        }

        AccBalanceTableEntity accBalanceTableEntityFrom = accBalanceEntityListFrom.get(0);
        if (accBalanceTableEntityFrom.getCurBalance() < amount || accBalanceTableEntityFrom.getCurBalance()<0) {
            return  R.error("余额不足");
        }

        return  R.ok("请输入支付密码");
    }
    /**
     * 发起转账事务
     *
     * @return string string
     *
     */
    @GlobalTransactional
    public R doTransactionCommit(TransferInfoEntity transferInfoEntity){


        Map<String, Object> map = new HashMap<>();
        //kay是字段名 value是字段值
        map.put("acc_id", transferInfoEntity.getAccIdFrom());
        //1数据初始化和检查
        Double amount = transferInfoEntity.getAmount();

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
            return R.error("账号错误, 账号不存在");
        }

        //判断账号是否异常
        AccInfoTableEntity accInfoTableEntityFrom = accInfoTableEntityFromList.get(0);
        if(!accInfoTableEntityFrom.getAccTitle().equals(transferInfoEntity.getAccTitleFrom())){
            return R.error("付方账号与用户名对不上");
        }

        if(accInfoTableEntityFrom.getAccStatus()==-1){
            return  R.error("账号错误, 账号存在异常");
        }

        //判断收方账号是否存在
        map.put("acc_id", transferInfoEntity.getAccIdTo());
        List<AccInfoTableEntity> accInfoTableEntityToList = accInfoTableService.listByMap(map);

        if(accInfoTableEntityToList.size()<1){
            return  R.error("信息错误, 付方账号不存在");
        }


        //判断收方账号是否存在异常
        AccInfoTableEntity accInfoTableEntityTo = accInfoTableEntityToList.get(0);
        if(!accInfoTableEntityTo.getAccTitle().equals(transferInfoEntity.getAccTitleTo())){
            return  R.error("收方账号与用户名对不上");
        }

        if(accInfoTableEntityTo.getAccStatus()==-1){
            return  R.error("账号错误,付方账号存在异常");
        }


        map.put("acc_id", transferInfoEntity.getAccIdFrom());
        List<AccBalanceTableEntity> accBalanceEntityListFrom = accBalanceTableService.listByMap(map);

//        System.out.println(accBalanceEntityListFrom.size());
        if(accBalanceEntityListFrom.size()==0){
            return  R.error("账户余额信息缺失");
        }else if(accBalanceEntityListFrom.size()>1){
            return  R.error("余额信息重复");
        }

        map.put("acc_id", transferInfoEntity.getAccIdTo());
        List<AccBalanceTableEntity> accBalanceEntityListTo = accBalanceTableService.listByMap(map);

        if(accBalanceEntityListTo.size()==0){
            return  R.error("收方账户余额信息缺失");
        }else if(accBalanceEntityListTo.size()>1){
            return  R.error("收方余额重复");
        }

        AccBalanceTableEntity accBalanceTableEntityFrom = accBalanceEntityListFrom.get(0);
        if (accBalanceTableEntityFrom.getCurBalance() < amount || accBalanceTableEntityFrom.getCurBalance()<0) {
            return  R.error("余额不足");
        }


        boolean result = false;
        //第一个TCC 事务参与者
        result = tccActionOne.prepare(null, transferInfoEntity,uuid,amount,accBalanceTableEntityFrom);
        if(!result){
            throw new RuntimeException("付方扣款失败.");
        }

        //第二个事务参与者
        //增加余额
        //增加收方明细
        System.out.println("开始收款账");
        AccBalanceTableEntity accBalanceTableEntityTo = accBalanceEntityListTo.get(0);

        result = tccActionTwo.prepare(null, transferInfoEntity,uuid,amount,accBalanceTableEntityTo);
        if(!result){
            throw new RuntimeException("收方收款失败.");
        }


        return  R.ok("转账正在进行中");
    }

    /**
     * Do transaction rollback string.
     *
     * @param map the map
     * @return the string
     */
//    @GlobalTransactional
//    public String doTransactionRollback(Map map){
//        //第一个TCC 事务参与者
//        boolean result = tccActionOne.prepare(null, 1);
//        if(!result){
//            throw new RuntimeException("TccActionOne failed.");
//        }
//        result = tccActionTwo.prepare(null, "two");
//        if(!result){
//            throw new RuntimeException("TccActionTwo failed.");
//        }
//        map.put("xid", RootContext.getXID());
//        throw new RuntimeException("transacton rollback");
//    }

    /**
     * Sets tcc action one.
     *
     * @param tccActionOne the tcc action one
     */
    public void setTccActionOne(TccActionOne tccActionOne) {
        this.tccActionOne = tccActionOne;
    }

    /**
     * Sets tcc action two.
     *
     * @param tccActionTwo the tcc action two
     */
    public void setTccActionTwo(TccActionTwo tccActionTwo) {
        this.tccActionTwo = tccActionTwo;
    }

    public String checkPassword(TransferInfoEntity transferInfoEntity) {
        String ret = "";
        String accId = transferInfoEntity.getAccIdFrom();
        String paypassword = transferInfoEntity.getPassword();
        AccInfoTableEntity accInfoTableEntity = accInfoTableService.getOne(new QueryWrapper<AccInfoTableEntity>().eq("acc_id",accId));
        if(paypassword.equals(accInfoTableEntity.getPayPassword())){
            //开始转账
            doTransactionCommit(transferInfoEntity);
        }else {
            return "密码错误";
        }

        return ret;
    }
}
