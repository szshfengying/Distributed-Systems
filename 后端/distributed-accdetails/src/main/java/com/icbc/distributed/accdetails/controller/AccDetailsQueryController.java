package com.icbc.distributed.accdetails.controller;


import com.icbc.common.utils.ParseJwt;
import com.icbc.common.utils.R;


import com.icbc.distributed.accdetails.entity.AccDetailQueryEntity;
import com.icbc.distributed.accdetails.service.AccDetailsTableService;
import com.icbc.distributed.accdetails.service.AccInfoQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query")
public class AccDetailsQueryController {

    @Autowired
    AccInfoQueryService accInfoQueryService;

    @Autowired
    private AccDetailsTableService accDetailsTableService;



    @RequestMapping(path = "/detail")
    public R accDetailQuery(@RequestBody AccDetailQueryEntity accDetailQueryEntity,@RequestHeader("token")String token){
        try {
            ParseJwt.parseJWT(token);
        }catch (
                Exception e
        ){
            return R.error(500,"wrong token");
        }
        String result = accInfoQueryService.detailQuery(accDetailQueryEntity);
        return R.ok().put("账号信息",result);
    }

}
