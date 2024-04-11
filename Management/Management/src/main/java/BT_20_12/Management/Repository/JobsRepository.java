package BT_20_12.Management.Repository;

import BT_20_12.Management.Entity.JobsEntity;
import BT_20_12.Management.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepository extends JpaRepository<JobsEntity,Integer> {
    public JobsEntity findByName(String name);
    JobsEntity findById(int id);
    @Query("SELECT u FROM JobsEntity u WHERE u.name LIKE %:def%")
    List<JobsEntity> findJobsEntitiesByName(@Param("def") String def);
}

