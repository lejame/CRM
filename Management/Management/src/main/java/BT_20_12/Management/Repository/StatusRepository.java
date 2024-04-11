package BT_20_12.Management.Repository;

import BT_20_12.Management.Entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity,Integer> {
    public StatusEntity findByName(String name);
}
