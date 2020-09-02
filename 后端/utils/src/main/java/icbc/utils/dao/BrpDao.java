package icbc.utils.dao;




import icbc.utils.entity.NthpaBrpTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrpDao extends JpaRepository<NthpaBrpTableEntity,Long> {
    List<NthpaBrpTableEntity> findByBrno(int brno);

    List<NthpaBrpTableEntity> findByBrnoName(String name);

    List<NthpaBrpTableEntity> findAllByZoneno(int zoneno);

}
