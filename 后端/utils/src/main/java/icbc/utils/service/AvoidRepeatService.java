package icbc.utils.service;

import java.util.Map;

public interface AvoidRepeatService {

    boolean checkTxn(String txnId);

    boolean checkAccount(String accId);
}
