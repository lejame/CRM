package BT_20_12.Management.Controller;


import BT_20_12.Management.Entity.*;

import BT_20_12.Management.Repository.UsersRepository;
import BT_20_12.Management.Services.RoleService;
import BT_20_12.Management.Services.StatusService;
import BT_20_12.Management.Services.TaskServices;
import BT_20_12.Management.Services.UsersService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@SessionAttributes("session")
@RequestMapping("/user")
public class UsersController {
    @Autowired
    public UsersService usersService;
    @Autowired
    private final RoleService roleService;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    TaskServices taskServices;
    @Autowired
    StatusService statusService;

    @Autowired
    HttpServletRequest request;
     UsersController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/profile_user")
    public String profileUser(@RequestParam("id") int id,Model model) {
        UsersEntity user = usersService.findUserById(id);
        List<TasksEntity> task = taskServices.findByidUser(id);
        if(task.isEmpty()){
            model.addAttribute("user",user);
            return "profile";
        }

        model.addAttribute("task",task);
        model.addAttribute("user",user);
        return "profile";
    }

    @GetMapping("/profile_user_edit")
    public String profileUserEdit(@RequestParam("id") int id, @RequestParam("id_user") int id_user, Model model, RedirectAttributes redirectAttributes) {
        List<StatusEntity> list_status = statusService.getAll();

        TasksEntity task = taskServices.findbyId(id);
        model.addAttribute("task",task);
        model.addAttribute("list_status",list_status);
        model.addAttribute("id_user",id_user);

        return "profile-edit";
    }
    @PostMapping("/profile_edit")
    public String edit(@RequestParam("id")int id,@RequestParam("name-job") String job,@RequestParam("name-task")String name,@RequestParam("select") String select,@RequestParam("date_start")String start
    ,@RequestParam("date_end")String end,Model model){
        TasksEntity task = taskServices.findbyId(id);
        StatusEntity status = statusService.findStatusbyName(select);
        task.setName(name);
        task.setStatus(status);
        task.getJob().setName(job);
        task.setStartDate(start);
        task.setEndDate(end);

        taskServices.Save(task);

        List<StatusEntity> list_status = statusService.getAll();

        TasksEntity task1 = taskServices.findbyId(id);
        model.addAttribute("task",task1);
        model.addAttribute("list_status",list_status);
        model.addAttribute("id_user",task.getUserId().getId());
        return "profile-edit";
    }
    @GetMapping("/form_user")
    public String formUser(Model model) {
        List<UsersEntity> list_user = usersService.getAllUser();
        if(list_user.isEmpty()){
            return "user-table";
        }
        Cookie[] cookies = request.getCookies();
        String cookieValue = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("email")) {
                    cookieValue += cookie.getValue();
                    System.out.println(cookieValue);
                }
            }
        }

        model.addAttribute("list_user",list_user);
        model.addAttribute("email",cookieValue);
        return "user-table";

    }

    @GetMapping("/form_add")
    public String formUserAdd(Model model) {
        List<String> countries = Arrays.asList("London", "India", "Usa", "Canada", "Thailand");
        List<RolesEntity> list_role = roleService.getRole();
        model.addAttribute("countries", countries);
        model.addAttribute("selectedCountry", "Usa");
        model.addAttribute("list_role", list_role);
        model.addAttribute("notice_check",false);
        model.addAttribute("notice_success",false);
        return "user-add";
    }
    // fix
    @PostMapping("/add_user")
    public String formUserAddSuccess(@RequestParam("full_name") String name,@RequestParam("email") String email,@RequestParam("password") String password,
                                           @RequestParam("phone") String phone,@RequestParam("country") String country,@RequestParam("role") String role,Model model){
        RolesEntity rolesEntity =  roleService.findRoleByName(role);

        if(rolesEntity==null){
            model.addAttribute("notice_check",true);
            return "user-add";
        }
        usersService.addnewMenber(name,email,password,phone,country,rolesEntity);
        model.addAttribute("notice_success",true);
        return "user-add";
    }
    @GetMapping("/form_edit")
    public String formEdit(@RequestParam("id") int id,Model model){
        UsersEntity user = usersService.findUserById(id);
        model.addAttribute("user_edit",user);
        List<String> countries = Arrays.asList("London", "India", "Usa", "Canada", "Thailand");
        List<RolesEntity> list_role = roleService.getRole();
        model.addAttribute("countries", countries);
        model.addAttribute("selectedCountry", "Usa");
        model.addAttribute("list_role", list_role);
        model.addAttribute("notice_check",false);
        model.addAttribute("notice_success",false);
        return "user-edit";
    }

    @GetMapping("/delete/{id}")
    public RedirectView form_delete(@PathVariable("id") int id){

        usersService.deleteuserById(id);
        return new RedirectView("/user/form_user");
    }

    @PostMapping("/edit")
    public String form_edit_user(@RequestParam("full_name") String name,@RequestParam("email") String email,@RequestParam("password") String password,
                                 @RequestParam("phone") String phone,@RequestParam("country") String country,@RequestParam("role") String role,Model model){
        UsersEntity user = usersService.findbyEmail(email);
        if(user ==null){
            model.addAttribute("notice_check",true);

            return "user-edit";
        }
        user.setFullname(name);
        user.setCountry(country);
        user.setPhonenumber(phone);
        user.setPassword(password);
        usersRepository.save(user);
        model.addAttribute("notice_success",true);

        return "user-add";
    }
    @GetMapping("/details")
    public String user_detail(Model model){
        Cookie[] cookies = request.getCookies();
        String cookieValue = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("email")) {
                    cookieValue += cookie.getValue();
                    System.out.println(cookieValue);
                }
            }
        }
        UsersEntity users = usersService.findbyEmail(cookieValue);
        List<TasksEntity> list_task = taskServices.getAllTask();
        List<TasksEntity> new_list_task= new ArrayList<>();
        for(TasksEntity list:list_task){
            if(list.getUserId().getId()==users.getId()){
                new_list_task.add(list);
            }
        }
        List<TasksEntity> chua_ht = new ArrayList<>();
        List<TasksEntity> dang_ht = new ArrayList<>();
        List<TasksEntity> da_ht = new ArrayList<>();
        for(TasksEntity list : new_list_task){
            if(list.getStatus().getId()==1){ // chua hoan thanh
                System.out.println(list.getName());
                chua_ht.add(list);
            }
            if (list.getStatus().getId()==2) { // dang hoan thanh
                System.out.println(list.getName());

                dang_ht.add(list);
            }
            if(list.getStatus().getId()==3) {
                System.out.println(list.getName());

                da_ht.add(list); // da hoan thanh
            }
        }
        if(chua_ht.isEmpty() && da_ht.isEmpty() && !dang_ht.isEmpty()){ // neu chua_ht ko co
            model.addAttribute("dang_ht",dang_ht);
        }
        if(!chua_ht.isEmpty() && da_ht.isEmpty() && dang_ht.isEmpty()){
            model.addAttribute("chua_ht",chua_ht);
        }
        if (chua_ht.isEmpty() && !da_ht.isEmpty() && dang_ht.isEmpty()) {
            model.addAttribute("da_ht",da_ht);
        }
        model.addAttribute("user", users);
        return "user-details";
    }
    /// Search
    @GetMapping("/search")
    public String searchUser(@RequestParam("searchInput") String searchValue, Model model) {
        List<UsersEntity> searchResults = usersRepository.findUsersByFullnameLike(searchValue);
        model.addAttribute("list_user",searchResults);
        return "user-table";
    }

}