package com.icbc.distributed.accinfo.controller;


import com.icbc.common.utils.R;
import com.icbc.distributed.accinfo.entity.AccInfoQueryEntity;
import com.icbc.distributed.accinfo.feign.FClient;
import com.icbc.distributed.accinfo.service.AccDetailsTableService;
import com.icbc.distributed.accinfo.service.AccInfoQueryService;
import com.icbc.common.utils.ParseJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/query")
public class AccInfoQueryController {

    @Autowired
    AccInfoQueryService accInfoQueryService;

    @Autowired
    private AccDetailsTableService accDetailsTableService;

    @Autowired
    FClient fClient;

    @RequestMapping(path= "/info")
    public R accInfoQuery(@RequestBody AccInfoQueryEntity accInfoQueryEntity,@RequestHeader("token")String token){
        try {
            ParseJwt.parseJWT(token);
        }catch (
                Exception e
        ){
            return R.error(500,"wrong token");
        }
//    public R accInfoQuery(@RequestBody AccInfoQueryEntity accInfoQueryEntity){
        if(fClient.checkAcc(accInfoQueryEntity.getAccId()+"")){
            return R.error(400, "wrong acc");
        }

        String result = accInfoQueryService.infoQuery(accInfoQueryEntity);
        return R.ok().put("账号信息",result);
    }
}
