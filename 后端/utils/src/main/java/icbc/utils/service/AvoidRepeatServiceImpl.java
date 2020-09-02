package icbc.utils.service;


import icbc.utils.dao.AccInfoDao;
import icbc.utils.dao.BizRegDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class AvoidRepeatServiceImpl implements AvoidRepeatService {

    @Autowired
    AccInfoDao accInfoDao;

    @Autowired
    BizRegDao bizRegDao;

    public boolean checkTxn(String txnId){
        if(bizRegDao.checkBiz(txnId)>0) return false;
        return true;
    }

    public boolean checkAccount(String accId){
        if(accInfoDao.checkAcc(accId)>0) return false;
        return true;
    }
}
