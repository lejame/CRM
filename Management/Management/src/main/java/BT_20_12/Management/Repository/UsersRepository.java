package BT_20_12.Management.Repository;

import BT_20_12.Management.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Integer> {
    UsersEntity findByEmailAndPassword(String email,String password);
    Boolean deleteById(int id);

    UsersEntity findByEmail(String name);
    UsersEntity findByFullname(String name);

    UsersEntity findById(int id);
    @Query("SELECT u FROM UsersEntity u WHERE u.fullname LIKE %:def%")
    List<UsersEntity> findUsersByFullnameLike(@Param("def") String def);

}
