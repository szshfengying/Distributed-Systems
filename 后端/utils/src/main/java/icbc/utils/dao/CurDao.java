package icbc.utils.dao;



import icbc.utils.entity.NthpaCurTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurDao extends JpaRepository<NthpaCurTableEntity,Long> {

    List<NthpaCurTableEntity> findAllByZoneno(int zoneno);

    List<NthpaCurTableEntity> findByCurrCode(short currCode);

    List<NthpaCurTableEntity> findByChinese(String name);

}
