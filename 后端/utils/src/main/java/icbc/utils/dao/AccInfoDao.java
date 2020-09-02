package icbc.utils.dao;


import icbc.utils.entity.AccInfoTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccInfoDao extends JpaRepository<AccInfoTableEntity,Long> {

    @Query(value = "select count(*) from acc_info_table where acc_id=?1",nativeQuery = true)
    int checkAcc(String accId);

}
