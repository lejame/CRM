package BT_20_12.Management.Services;

import BT_20_12.Management.Entity.JobsEntity;
import BT_20_12.Management.Entity.StatusEntity;
import BT_20_12.Management.Entity.TasksEntity;
import BT_20_12.Management.Entity.UsersEntity;
import BT_20_12.Management.Repository.JobsRepository;
import BT_20_12.Management.Repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServices {
    @Autowired
    TasksRepository tasksRepository;
    @Autowired
    JobsRepository jobsRepository;
    @Autowired
    UsersService usersService;
    public List<TasksEntity> getAllTask(){
        return tasksRepository.findAll();
    }
    public void saveTask(String name_task,String date_start,String date_end,JobsEntity job, UsersEntity user, StatusEntity status){
        tasksRepository.save(new TasksEntity(name_task,date_start,date_end,job,user,status));
    }
    public TasksEntity findbyId(int id){
        return tasksRepository.findById(id);
    }
    public TasksEntity findByidJob(int id){

        return tasksRepository.findByJobOrderById(jobsRepository.findById(id));}

    public List<TasksEntity> findByidUser(int id){
        return tasksRepository.findByUserIdOrderById(usersService.findUserById(id));
    }

    public void Save(TasksEntity task){
        tasksRepository.save(task);
    }

}
