package com.icbc.distributed.accopen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icbc.common.exception.MsgCode.BaseCode;
import com.icbc.common.exception.RRException;
import com.icbc.distributed.common.IdWorker;
import com.icbc.distributed.common.Luhn;
import com.icbc.distributed.accopen.dao.*;
import com.icbc.distributed.accopen.entity.*;
import com.icbc.distributed.accopen.service.RegisterAccService;
import com.icbc.distributed.accopen.vo.request.AccOpenEntity;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
public class RegisterAccServiceImpl implements RegisterAccService {

    @Autowired
    AccIdTableDao accIdTableDao;
    @Autowired
    AccInfoTableDao accInfoTableDao;
    @Autowired
    BizRegTableDao bizRegTableDao;
    @Autowired
    AccBalanceTableDao accBalanceTableDao;
    @Autowired
    AccDetailsTableDao accDetailsTableDao;
    @Autowired
    AccAgrtTableDao accAgrtTableDao;
    @Autowired
    NthpaBrpTableDao nthpaBrpTableDao;
    @Autowired
    NthpaZonTableDao nthpaZonTableDao;

    @Override
    @Transactional
    public String registerAcc(AccOpenEntity accOpenEntity) {

        ///唯一编号生成
        Date tradeDate =new Date();
        IdWorker idWorker = new IdWorker();
        Long uuid = idWorker.nextId();

        //交易日期
        AccIdTableEntity accIdTableEntity = new AccIdTableEntity();
        accIdTableEntity.setLastModifyDate(tradeDate);

        //地区转地区号
        NthpaBrpTableEntity entity=nthpaBrpTableDao.selectOne(new QueryWrapper<NthpaBrpTableEntity>()
                .eq("brno_name",accOpenEntity.getBranchId()));
        Integer regionId =Integer.valueOf(entity.getZoneno());

        accIdTableEntity.setRegionId(regionId);
        accIdTableEntity.setTypeId(0);
        accIdTableEntity.setAccId("1");
        accIdTableEntity.setNumber(accOpenEntity.getNumber());
        accIdTableEntity.setPhone(accOpenEntity.getPhone());
        String tableId = generateTableId(accIdTableEntity);
        String accId = generateAccId(accOpenEntity,accIdTableEntity,tableId);


        ///唯一编号查重
        Map<String ,Object>  bizRegMap=new HashMap<>();
        bizRegMap.put("txn_id",uuid);
        List<BizRegTableEntity> result=bizRegTableDao.selectByMap(bizRegMap);
        if(result ==null||!result.isEmpty()){//查重结果不能是null也不能非空
            throw new RRException(BaseCode.BUSINESS_REPEAT_EXCEPTION);
        }

        //账户查重
        Map<String, Object> map = new HashMap<>();
        map.put("acc_id", accId);
        List<AccInfoTableEntity> accInfoTableEntities= accInfoTableDao.selectByMap(map);

        if(accInfoTableEntities == null || !accInfoTableEntities.isEmpty()){
            throw new RRException(BaseCode.CHECK_REPEAT_EXCEPTION);
        }

        //更新数据库
        accIdTableEntity.setAccId(accId);
        accIdTableDao.insert(accIdTableEntity);
        updateDateBase(accOpenEntity, accIdTableEntity, tradeDate, accId,uuid);
        return accId;
    }

    private void updateDateBase(AccOpenEntity accOpenEntity, AccIdTableEntity accIdTableEntity, Date tradeDate, String accId,Long uuid)  {

        //table2用户信息表
        AccInfoTableEntity accInfoTableEntity = new AccInfoTableEntity();
        accInfoTableEntity.setAccId(accId);
        accInfoTableEntity.setAccStatus(1);
        accInfoTableEntity.setAccTitle(accOpenEntity.getAccTitle());
        accInfoTableEntity.setPassword(DigestUtils.md5Hex(accOpenEntity.getPassword() + accId));
        accInfoTableEntity.setDepositSign(1);
        accInfoTableEntity.setExchangeSign(1);
        accInfoTableEntity.setDepositSwitch(1);
        accInfoTableEntity.setWithdrawalSwitch(1);
        accInfoTableEntity.setOpeningInstitution(accOpenEntity.getExecOrganno());
        accInfoTableEntity.setLastModifiedDate(tradeDate);
        accInfoTableEntity.setSigningDate(tradeDate);
        accInfoTableEntity.setPayPassword(DigestUtils.md5Hex(accOpenEntity.getPayPassword()+ accId));
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try{
            accInfoTableEntity.setLogoutDate(simpleDateFormat.parse("9999-12-30"));
        }catch (ParseException ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        //地区号
        accInfoTableEntity.setRegionId(accIdTableEntity.getRegionId());
        accInfoTableDao.insert(accInfoTableEntity);

    //table3业务登记簿
        BizRegTableEntity bizRegTableEntity=new BizRegTableEntity();
        bizRegTableEntity.setTxnDate(tradeDate);
        bizRegTableEntity.setTxnId(String.valueOf(uuid));
        bizRegTableEntity.setChannelType(1);
        bizRegTableEntity.setExecOrganno(accOpenEntity.getExecOrganno()); //操作机构
        bizRegTableEntity.setExecTellerno(accOpenEntity.getExecTellerno());
        bizRegTableEntity.setTxnCode(1);
        bizRegTableEntity.setTxnType(2);
        bizRegTableEntity.setCashTransferFlag(1);
        bizRegTableEntity.setDebitAccId("null");
        bizRegTableEntity.setDebitAccName("null");
        bizRegTableEntity.setCreditCurrType(0);
        bizRegTableEntity.setCreditAmount(0l);
        bizRegTableEntity.setStatus(0);
        bizRegTableEntity.setLastModifyDate(tradeDate);
        bizRegTableEntity.setRegionId(accIdTableEntity.getRegionId());//地区号
        bizRegTableEntity.setDebitCurrType(0);
        bizRegTableEntity.setDebitAmount(0l);
        bizRegTableEntity.setCreditAccId("null");
        bizRegTableEntity.setCreditAccName("null");
        bizRegTableDao.insert(bizRegTableEntity);


        //table4新增账户余额
        AccBalanceTableEntity accBalanceTableEntity =new AccBalanceTableEntity();
        accBalanceTableEntity.setAccId(accId);
        accBalanceTableEntity.setCurrType(0);
        accBalanceTableEntity.setCurBalance(0l);
        accBalanceTableEntity.setYdayBalance(0l);
        accBalanceTableEntity.setLastTxnDate(tradeDate);
        accBalanceTableEntity.setRegionId(accIdTableEntity.getRegionId());//调用公共层方法地区转地区号
        accBalanceTableDao.insert(accBalanceTableEntity);



        //table5新增账户明细表
        AccDetailsTableEntity accDetailsTableEntity=new AccDetailsTableEntity();
        accDetailsTableEntity.setDepartId(1);//分区编号
        accDetailsTableEntity.setAccId(accId);
        accDetailsTableEntity.setCurrType(0);
        accDetailsTableEntity.setTxnId(String.valueOf(uuid));
        accDetailsTableEntity.setExecOrganno(accOpenEntity.getExecOrganno()); //操作机构 ，调用公共层方法操作机构转操作机构号
        accDetailsTableEntity.setExecTellerno(accOpenEntity.getExecTellerno());
        accDetailsTableEntity.setExecDate(tradeDate);
        accDetailsTableEntity.setTxnCode(0);
        accDetailsTableEntity.setCashFlag(1);
        accDetailsTableEntity.setLoanFlag(0);
        accDetailsTableEntity.setAmount(0l);
        accDetailsTableEntity.setBalance(0l);
        accDetailsTableEntity.setBalance(0l);
        accDetailsTableEntity.setRegionId(accIdTableEntity.getRegionId());//地区号
        accDetailsTableDao.insert(accDetailsTableEntity);

        //table6新增存款协议表
        AccAgrtTableEntity accAgrtTableEntity=new AccAgrtTableEntity();
        accAgrtTableEntity.setAccId(accId);
        accAgrtTableEntity.setCurrType(0);
        accAgrtTableEntity.setRateIncm(1);
        accAgrtTableEntity.setRateAltf(5);
        accAgrtTableEntity.setBaseRate(0);
        accAgrtTableEntity.setFloaType(0);
        accAgrtTableEntity.setFloaRate(0);
        accAgrtTableEntity.setLastRateDate(tradeDate);
        accAgrtTableEntity.setLastOrganno(accOpenEntity.getExecOrganno()); //操作机构 ，调用公共层方法操作机构转操作机构号
        accAgrtTableEntity.setLastTellerno(String.valueOf(accOpenEntity.getExecTellerno()));
        accAgrtTableEntity.setLastModifyDate(tradeDate);
        accAgrtTableEntity.setRegionId(accIdTableEntity.getRegionId());//地区号
        accAgrtTableDao.insert(accAgrtTableEntity);
    }

    private String generateAccId(AccOpenEntity accOpenEntity,AccIdTableEntity accIdTableEntity, String tableId) {

        //地区号
        Integer regionId = accIdTableEntity.getRegionId();
        //网点号
        NthpaBrpTableEntity entity=nthpaBrpTableDao.selectOne(new QueryWrapper<NthpaBrpTableEntity>()
                .eq("brno_name",accOpenEntity.getBranchId()));
        String branchId=entity.getBrno();
        //common增加isNumber判断
        if(branchId == null && branchId.length() != 4){
            throw new RRException(BaseCode.ACC_ID_EXCEPTION);
        }

        String accId = regionId + branchId + tableId;

        Luhn luhn= new Luhn(accId);
        int sumId = luhn.getCheckSum();

        return accId + sumId;
    }

    String generateTableId(AccIdTableEntity accIdTableEntity){

        //数据库自增产生分布式id
        accIdTableDao.insertAndReturnId(accIdTableEntity);

        String accIdString = String.valueOf(accIdTableEntity.getSnId());
        int length = accIdString.length();

        //转为为七位tableId
        if(Integer.valueOf(accIdTableEntity.getAccId()) < 0 || length > 7){
            throw new RRException(BaseCode.TABLE_ID_OVERFlW);
        }
        StringBuilder zero = new StringBuilder();
        for(int i = 0; i < 7 - length; i++){
            zero.append("0");
        }
        String accId = zero.toString() + accIdString;
        //System.out.println("--------------"+ accId +"------------");

        AccIdTableEntity updateEntity = new AccIdTableEntity();
        updateEntity.setAccId(accId);
        accIdTableDao.update(updateEntity, new QueryWrapper<AccIdTableEntity>()
                .eq("sn_id", accIdTableEntity.getSnId())
                .eq("region_id", accIdTableEntity.getRegionId()));
        return accId;
    }
}
