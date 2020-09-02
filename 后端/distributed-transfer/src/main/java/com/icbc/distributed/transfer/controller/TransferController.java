package com.icbc.distributed.transfer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icbc.common.utils.ParseJwt;
import com.icbc.common.utils.R;
import com.icbc.distributed.transfer.entity.AccBalanceTableEntity;
import com.icbc.distributed.transfer.entity.AccInfoTableEntity;
import com.icbc.distributed.transfer.entity.TransferInfoEntity;
import com.icbc.distributed.transfer.tccservice.TccTransactionService;
import com.icbc.distributed.transfer.service.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RefreshScope
@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    TccTransactionService transferService;


    @Autowired
    private AccBalanceTableService accBalanceTableService;


    @Autowired
    private AccDetailsTableService accDetailsTableService;

    @Autowired
    private BizRegTableService bizRegTableService;


    @Autowired
    private AccInfoTableService accInfoTableService;

//    @RequestBody TransferInfoEntity transferInfoEntity

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public R checkAcc(@RequestBody TransferInfoEntity transferInfoEntity) throws Exception {

        System.out.println("账号检查");
        System.out.println(transferInfoEntity);

        try {

        R ret = transferService.checkAcc(transferInfoEntity);
            return ret;
        }catch (Throwable t){

            t.printStackTrace();
            return R.error("账号异常");
        }


    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    @ResponseBody
    public R CheckPayPassword(@RequestBody TransferInfoEntity transferInfoEntity,@RequestHeader("token") String token){

        try{
            ParseJwt.parseJWT(token);
        }catch (Exception e){
            return R.error(500,"wrong token");
        }

        String accId = transferInfoEntity.getAccIdFrom();

        String paypassword= DigestUtils.md5Hex(transferInfoEntity.getPassword() + transferInfoEntity.getAccIdFrom());
        AccInfoTableEntity accInfoTableEntity = accInfoTableService.getOne(new QueryWrapper<AccInfoTableEntity>().eq("acc_id",accId));
        if(accInfoTableEntity == null){
            return R.error("账号不存在");
        }
        System.out.println(paypassword+"\n");
        System.out.println(accInfoTableEntity.getPayPassword());
        if(paypassword.equals(accInfoTableEntity.getPayPassword())){
            //开始转账
            try {
                R ret = transferService.doTransactionCommit(transferInfoEntity);
                return ret;
            }catch (Throwable t){
                t.printStackTrace();
                return R.error("转账异常");
            }


        }else {
            return R.error("密码错误");
        }

    }



    @PostMapping("/accId/{accId}")
    public Collection<AccBalanceTableEntity> getListMap(@PathVariable("accId") Long accId) {
        Map<String, Object> map = new HashMap<>();
        //kay是字段名 value是字段值
        map.put("acc_id", accId);

        List<AccBalanceTableEntity> accBalanceEntityList = accBalanceTableService.listByMap(map);
        AccBalanceTableEntity accBalanceTableEntity = accBalanceEntityList.get(0);
        accBalanceTableEntity.setCurBalance(100 + accBalanceTableEntity.getCurBalance());
        accBalanceTableService.saveOrUpdate(accBalanceTableEntity);
        return accBalanceEntityList;
    }

}
