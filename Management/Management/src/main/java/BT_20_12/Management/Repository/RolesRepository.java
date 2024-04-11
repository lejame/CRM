package BT_20_12.Management.Repository;

import BT_20_12.Management.Entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity,Integer> {
    public RolesEntity findByName(String name);
    RolesEntity findById(int id);

}
