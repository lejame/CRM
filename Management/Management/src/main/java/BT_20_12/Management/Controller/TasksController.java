package BT_20_12.Management.Controller;

import BT_20_12.Management.Entity.JobsEntity;
import BT_20_12.Management.Entity.StatusEntity;
import BT_20_12.Management.Entity.TasksEntity;
import BT_20_12.Management.Entity.UsersEntity;
import BT_20_12.Management.Repository.StatusRepository;
import BT_20_12.Management.Repository.TasksRepository;
import BT_20_12.Management.Services.JobsService;
import BT_20_12.Management.Services.StatusService;
import BT_20_12.Management.Services.TaskServices;
import BT_20_12.Management.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/task")
public class TasksController {
    @Autowired
    JobsService jobsService;

    @Autowired
    UsersService usersService;

    @Autowired
    TaskServices taskServices;

    @Autowired
    StatusService statusService;
    @Autowired
    TasksRepository tasksRepository;
    @GetMapping("/form")
    public String task(Model model){
        List<TasksEntity> task = taskServices.getAllTask();
        if(task.isEmpty()){
            return "task";
        }
        model.addAttribute("list_task",task);
        return "task";
    }

    @GetMapping("/form_add")
    public String task_add(Model model){
        List<JobsEntity> list_job = jobsService.getAll();
        List<UsersEntity> list_user = usersService.getAllUser();
        List<TasksEntity> list_task = taskServices.getAllTask();
        List<StatusEntity> list_status = statusService.getAll();
//        List<UsersEntity> newList = new ArrayList<>();

// Tạo một danh sách chứa ID người dùng đã thực hiện công việc
//        Set<Integer> userIds = new HashSet<>();
//        for (TasksEntity task : list_task) {
//            userIds.add(task.getUserId().getId());
//        }

// Lọc danh sách người dùng và chỉ thêm vào newList nếu không nằm trong danh sách người đã thực hiện công việc
//        for (UsersEntity user : list_user) {
//            if (!userIds.contains(user.getId())) {
//                newList.add(user);
//            }
//        }

        if (list_job.isEmpty() || list_user.isEmpty() || list_status.isEmpty()) {
            return "task-add";
        }

        model.addAttribute("notice_success", false);
        model.addAttribute("list_job", list_job);
        model.addAttribute("list_user", list_user);
        model.addAttribute("list_status", list_status);
        return "task-add";
    }
    @PostMapping("/add")
    public String add_task(@RequestParam("name_job") String name_job,@RequestParam("name_task")String name_task,
                           @RequestParam("name_user")String name_user,@RequestParam("date_start")String date_start,@RequestParam("date_end") String date_end,
                           @RequestParam("status") String name_status,Model model){
        try{
            UsersEntity user = usersService.findbyName(name_user);
            JobsEntity job = jobsService.findJobbyName(name_job);
            StatusEntity status = statusService.findStatusbyName(name_status);
            taskServices.saveTask(name_task,date_start,date_end,job,user,status);
            model.addAttribute("notice_success",true);
        }catch (Exception e){
            System.out.print(e.getSuppressed());
        }
        return "task-add";

    }

    @GetMapping("/form_edit")
    public String form_edit(Model model,@RequestParam("id") int id){
        TasksEntity task = taskServices.findbyId(id);
        List<JobsEntity> list_job = jobsService.getAll();
        List<UsersEntity> list_user = usersService.getAllUser();
        List<StatusEntity> list_status = statusService.getAll();
        if(list_job.isEmpty() || list_user.isEmpty() || list_status.isEmpty()) {
            return "task-edit";
        }
        model.addAttribute("notice_success",false);
        model.addAttribute("list_job",list_job);
        model.addAttribute("list_user",list_user);
        model.addAttribute("list_status",list_status);
        model.addAttribute("task",task);
        return "task-edit";
    }
    @PostMapping("/edit")
    public String edit_task(@RequestParam("id") int id, @RequestParam("name_job") String name_job, @RequestParam("name_task")String name_task,
                            @RequestParam("name_user")String name_user, @RequestParam("date_start")String date_start, @RequestParam("date_end") String date_end,
                            @RequestParam("status") String name_status, Model model, RedirectAttributes redirectAttributes){
        TasksEntity tasks = taskServices.findbyId(id);
        UsersEntity user = usersService.findbyName(name_user);
        StatusEntity status = statusService.findStatusbyName(name_status);
        tasks.setName(name_task);
        tasks.getJob().setName(name_job);
        tasks.setStartDate(date_start);
        tasks.setEndDate(date_end);
        tasks.setUserId(user);
        tasks.setStatus(status);
        tasksRepository.save(tasks);
        model.addAttribute("notice_success",true);

        redirectAttributes.addAttribute("id",id);

        List<JobsEntity> list_job = jobsService.getAll();

        List<UsersEntity> list_user = usersService.getAllUser();
        List<StatusEntity> list_status = statusService.getAll();
        if(list_job.isEmpty() || list_user.isEmpty() || list_status.isEmpty()) {
            return "task-edit";
        }

        model.addAttribute("list_job",list_job);
        model.addAttribute("list_user",list_user);

        model.addAttribute("list_status",list_status);
        model.addAttribute("task",tasks);
        return "task-edit";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id){
        tasksRepository.deleteById(id);
        return "redirect:/task/form";
    }

    @GetMapping("/search")
    public String search_task(@RequestParam("searchInput") String search,Model model){
        String user = search;
        String Job = search;
        List<TasksEntity> task = tasksRepository.findTasksEntitiesByNameAndUserIdAndJob_Name(search,user,Job);
        model.addAttribute("list_task",task);
        return "task";
    }
}
