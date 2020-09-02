package icbc.utils.controller;


import icbc.utils.service.AvoidRepeatService;
import icbc.utils.service.BrpService;
import icbc.utils.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilController {
    @Autowired
    AvoidRepeatService avoidRepeatService;

    @Autowired
    BrpService brpService;

    @Autowired
    RegionService regionService;

    @RequestMapping("/checkAcc")
    public boolean checkAcc(@RequestParam("accId")String accId){
        return avoidRepeatService.checkAccount(accId);
    }

    @RequestMapping("/checkTxn")
    public boolean checkTxn(@RequestParam("txnId")String txnId){
        return avoidRepeatService.checkTxn(txnId);
    }

    @RequestMapping("/getBrpId")
    public int getBrpId(@RequestParam("brpName")String brpName){
        return brpService.findIdByName(brpName);
    }

    @RequestMapping("/getZoneId")
    public int getZoneId(@RequestParam("zoneName")String zoneName){
        return regionService.findIdByName(zoneName);
    }
}
