package BT_20_12.Management.Repository;

import BT_20_12.Management.Entity.JobsEntity;
import BT_20_12.Management.Entity.TasksEntity;
import BT_20_12.Management.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<TasksEntity,Integer> {
    TasksEntity findById(int id);
    TasksEntity findByJobOrderById(JobsEntity job);
    List<TasksEntity> findByUserIdOrderById(UsersEntity users);

    @Query("SELECT u FROM TasksEntity u WHERE u.name LIKE %:def% or u.userId.fullname LIKE %:user% or u.job.name LIKE %:job%")
    List<TasksEntity> findTasksEntitiesByNameAndUserIdAndJob_Name(@Param("def") String def,@Param("user") String user,@Param("job") String job);

}
