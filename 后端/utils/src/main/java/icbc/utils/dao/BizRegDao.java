package icbc.utils.dao;


import icbc.utils.entity.BizRegTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BizRegDao extends JpaRepository<BizRegTableEntity,Long> {

    @Query(value = "select count(*) from biz_reg_table where txn_id=?1",nativeQuery = true)
    int checkBiz(String txnId);

}
