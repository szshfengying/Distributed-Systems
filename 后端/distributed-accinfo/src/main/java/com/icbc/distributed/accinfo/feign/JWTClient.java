package com.icbc.distributed.accinfo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("distributed-accopen")
@Component
public interface JWTClient {
    @RequestMapping("/checkAcc")
    boolean checkAcc(@RequestParam("accId") String accId);

    @RequestMapping("/checkTxn")
    boolean checkTxn(@RequestParam("txnId") String txnId);

    @RequestMapping("/getBrpId")
    int getBrpId(@RequestParam("brpName") String brpName);

    @RequestMapping("/getZoneId")
    int getZoneId(@RequestParam("zoneName") String zoneName);
}
