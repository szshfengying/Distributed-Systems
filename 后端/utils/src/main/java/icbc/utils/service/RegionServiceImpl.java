package icbc.utils.service;




import icbc.utils.dao.ZonDao;
import icbc.utils.entity.NthpaZonTableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class RegionServiceImpl implements RegionService {
    @Autowired
    ZonDao dao;


    public int findIdByName(String notes){
        List<NthpaZonTableEntity> result=dao.findByProvince(notes);
        if(result.size()>0){
            return result.get(0).getZoneno();
        }
        return -1;
    }

}
