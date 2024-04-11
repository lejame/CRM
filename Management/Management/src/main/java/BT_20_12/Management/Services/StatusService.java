package BT_20_12.Management.Services;

import BT_20_12.Management.Entity.StatusEntity;
import BT_20_12.Management.Repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    @Autowired
    StatusRepository statusRepository;
    public StatusEntity findStatusbyName(String name){
        return statusRepository.findByName(name);
    }
    public List<StatusEntity> getAll(){
        return statusRepository.findAll();
    }
}
