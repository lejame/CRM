package BT_20_12.Management.Controller;

import BT_20_12.Management.Entity.JobsEntity;
import BT_20_12.Management.Entity.TasksEntity;
import BT_20_12.Management.Repository.JobsRepository;
import BT_20_12.Management.Services.JobsService;
import BT_20_12.Management.Services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/job")
public class JobsController {
    @Autowired
    JobsService jobsService;
    @Autowired
    TaskServices taskServices;
    @Autowired
    JobsRepository jobsRepository;
    @GetMapping("/form")
    public String work(Model model){
        List<JobsEntity> list_job = jobsService.getlistJobs();
        if(list_job.isEmpty()){
            return "groupwork";

        }
        model.addAttribute("list_jobs",list_job);
        System.out.println(list_job.get(0).getName());
        return "groupwork";

    }
    @GetMapping("/form_add")
    public String form_add(Model model){
        model.addAttribute("notice_success",false);
        return "groupwork-add";
    }
    @PostMapping("/add")
    public String form_add_work(@RequestParam("name-job") String name, @RequestParam("date-start") String start, @RequestParam("date-end") String end, Model model) {

        model.addAttribute("notice_success",true);
        jobsService.save_jobs(name,start,end);
        return "groupwork-add";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id){
        jobsService.delete(id);
        return "redirect:/job/form";
    }

    @GetMapping("/form_detail")
    public String form_details(@RequestParam("id") int id,Model model){
        TasksEntity task = taskServices.findByidJob(id);
        model.addAttribute("task",task);
        return "groupwork-details";
    }
    public static int id_change;
    @GetMapping("/profile_edit")
    public String profile_edit(@RequestParam("id") int id, Model model){
        JobsEntity job = jobsService.findById(id);
        TasksEntity task = taskServices.findbyId(id);
        id_change = job.getId();
        model.addAttribute("task",task);
        model.addAttribute("job",job);
        return "groupwork-edit";
    }
    @PostMapping("/edit")
    public String edit_form(@RequestParam("name-job") String name, @RequestParam("date-start") String start, @RequestParam("date-end") String end, Model model){
        JobsEntity jobs = jobsService.findJobbyName(name);
        jobs.setName(name);
        jobs.setStartDate(start);
        jobs.setEndDate(end);
        jobsService.Save(jobs);
        model.addAttribute("notice_success",true);
        return "groupwork-add";
    }

    @GetMapping("/search")
    public String search_job(@RequestParam("searchInput") String search,Model model){
        List<JobsEntity> list_job = jobsRepository.findJobsEntitiesByName(search);
        model.addAttribute("list_jobs",list_job);
        return "groupwork";
    }

}
