package BT_20_12.Management.Services;

import BT_20_12.Management.Entity.RolesEntity;
import BT_20_12.Management.Entity.UsersEntity;
import BT_20_12.Management.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }
    public UsersEntity getUsersEntityByEmailAndAndPassword(String email,String password){
        UsersEntity user = usersRepository.findByEmailAndPassword(email,password);
        return user;
    }
    public boolean findUserHasRole(int idRole){
        List<UsersEntity> list = usersRepository.findAll();
        for(UsersEntity user: list){
            if(user.getRolesEntity().getId()==idRole){
                return true;
            }
        }
        return false;
    }
    public Boolean addnewMenber(String fullname, String email, String passwrod, String phonenumber, String country, RolesEntity role){
        usersRepository.save(new UsersEntity(email,passwrod,fullname,phonenumber,country,role));
        return true;
    }
    public List<UsersEntity> getAllUser(){
        return usersRepository.findAll();
    }

    public Boolean deleteuserById(int id){
        return usersRepository.deleteById(id);
    }

    public UsersEntity findbyEmail(String name){return usersRepository.findByEmail(name);}
    public UsersEntity findbyName(String name){return usersRepository.findByFullname(name);}

    public UsersEntity findUserById(int id){
        return usersRepository.findById(id);
    }
}
