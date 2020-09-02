package com.icbc.distributed.transfer.action;

import com.icbc.distributed.transfer.entity.AccBalanceTableEntity;
import com.icbc.distributed.transfer.entity.TransferInfoEntity;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * The interface Tcc action one.
 *
 * @author zhangsen
 */
@LocalTCC
public interface TccActionOne {

    /**
     * Prepare boolean.
     *
     * @param actionContext the action context
     * @param
     * @return the boolean
     */
    @TwoPhaseBusinessAction(name = "TccActionOne" , commitMethod = "commit", rollbackMethod = "rollback")
    public boolean prepare(BusinessActionContext actionContext,
                           @BusinessActionContextParameter(paramName = "transferInfoEntity") TransferInfoEntity transferInfoEntity,
                           @BusinessActionContextParameter(paramName = "uuid") String uuid,
                           @BusinessActionContextParameter(paramName = "amount") Double amount,
                           @BusinessActionContextParameter(paramName = "accBalanceTableEntityFrom") AccBalanceTableEntity accBalanceTableEntityFrom);

    /**
     * Commit boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    public boolean commit(BusinessActionContext actionContext);

    /**
     * Rollback boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    public boolean rollback(BusinessActionContext actionContext);
}
