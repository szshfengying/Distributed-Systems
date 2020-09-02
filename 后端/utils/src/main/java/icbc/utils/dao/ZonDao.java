package icbc.utils.dao;



import icbc.utils.entity.NthpaZonTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZonDao extends JpaRepository<NthpaZonTableEntity,Long> {

    List<NthpaZonTableEntity> findByZoneno(int zoneno);

    List<NthpaZonTableEntity> findByProvince(String province);

}
