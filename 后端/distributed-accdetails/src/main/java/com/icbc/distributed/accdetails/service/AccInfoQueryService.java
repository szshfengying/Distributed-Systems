package com.icbc.distributed.accdetails.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import  com.icbc.distributed.common.IdWorker;
import com.icbc.distributed.accdetails.entity.AccDetailQueryEntity;
import com.icbc.distributed.accdetails.entity.AccDetailsTableEntity;
import com.icbc.distributed.accdetails.entity.BizRegTableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AccInfoQueryService {

    @Autowired
    AccInfoTableService accInfoTableService;

    @Autowired
    BizRegTableService bizRegTableService;

    @Autowired
    IdWorker idWorker;

    @Autowired
    AccDetailsTableService accDetailsTableService;


    public String detailQuery(AccDetailQueryEntity accDetailQueryEntity) {
        //1.数据初始化和检查处理
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
        accDetailQueryEntity.setTxnId(uuid);

        HashMap<String,Object> map = new HashMap(16);

        //2.新增业务登记簿,业务类型为查询

        BizRegTableEntity bizRegTableEntity = new BizRegTableEntity();
//        bizRegTableEntity.setSnId(idWorker.nextId());//记录序号
        bizRegTableEntity.setTxnDate(accDetailQueryEntity.getTxnDate());//交易日期
        bizRegTableEntity.setTxnId(accDetailQueryEntity.getTxnId());//业务唯一编码
        bizRegTableEntity.setChannelType(1);//渠道种类
        bizRegTableEntity.setExecOrganno(accDetailQueryEntity.getExecOrganno());//操作机构
        bizRegTableEntity.setExecTellerno(accDetailQueryEntity.getExecTellerno());//操作柜员
        bizRegTableEntity.setTxnCode(111);//交易代码
        bizRegTableEntity.setTxnType(111);//交易类型
        bizRegTableEntity.setCashTransferFlag(1);//现金转账标志
        bizRegTableEntity.setDebitAccId("null");//借方账号
        bizRegTableEntity.setDebitAccName("null");//借方名称
        bizRegTableEntity.setDebitCurrType(1);//借方币种 transferInfoEntity.getCurrType()
        bizRegTableEntity.setDebitAmount(0L);//借方发生额
        bizRegTableEntity.setCreditAccId("null");//贷方账号
        bizRegTableEntity.setCreditAccName("null");//贷方名称
        bizRegTableEntity.setCreditCurrType(1);//贷方币种  transferInfoEntity.getCurrType()
        bizRegTableEntity.setCreditAmount(0L);//贷方发生额
        bizRegTableEntity.setStatus(1);//状态1-处理中2-交易
        bizRegTableEntity.setLastModifyDate(accDetailQueryEntity.getTxnDate());//最后更新日期
        bizRegTableEntity.setRegionId(1);//地区编号

        System.out.println(bizRegTableEntity.toString());
        bizRegTableService.save(bizRegTableEntity);

        //3.查询账号明细表
        map.put("acc_id", accDetailQueryEntity.getAccId());

//        System.out.println(accDetailQueryEntity.toString());
        System.out.println(accDetailQueryEntity);

//        PageUtils page = accDetailsTableService.queryPage(map);

//        System.out.println(page.toString());

        List<AccDetailsTableEntity> accDetailsTableEntitiesList = accDetailsTableService.listByMap(map);
        if(accDetailsTableEntitiesList.size()<1){
            return "账号没有明细";
        }
//        AccDetailsTableEntity accDetailsTableEntity  = accDetailsTableEntitiesList.get(0);
        //4.转json
        String detailsjson = JSON.toJSONStringWithDateFormat(accDetailsTableEntitiesList, "yyyy-MM-dd");
//        String detailsjson = JSONArray.toJSONString(accDetailsTableEntitiesList);//把list转换成json格式的String类型
        System.out.println(detailsjson);

        return detailsjson;

    }




}
