package BT_20_12.Management.Services;

import BT_20_12.Management.Entity.JobsEntity;
import BT_20_12.Management.Repository.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JobsService {
    @Autowired
    JobsRepository jobsRepository;
    public List<JobsEntity> getlistJobs(){return jobsRepository.findAll();}

    public boolean save_jobs(String name ,String start, String end){
        jobsRepository.save(new JobsEntity(name,start,end));
        return true;
    }
    public void Save(JobsEntity job){
        jobsRepository.save(job);
    }

    public void delete(int id){
        System.out.println(jobsRepository.findById(id));

        jobsRepository.deleteById(id);
    }
    public List<JobsEntity> getAll(){
        return jobsRepository.findAll();
    }
    public JobsEntity findJobbyName(String name){
        return jobsRepository.findByName(name);
    }
    public JobsEntity findById(int id){
        return jobsRepository.findById(id);
    }
}
