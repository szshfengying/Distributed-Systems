package icbc.utils.service;



import icbc.utils.dao.BrpDao;
import icbc.utils.entity.NthpaBrpTableEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class BrpServiceImpl implements BrpService {

    @Autowired
    BrpDao dao;


    public int findIdByName(String name){
        List<NthpaBrpTableEntity> result=dao.findByBrnoName(name);
        if(result.size()>0){
            return result.get(0).getBrno();
        }
        return -1;
    }

}
