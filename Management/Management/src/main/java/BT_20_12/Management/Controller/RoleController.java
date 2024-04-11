package BT_20_12.Management.Controller;

import BT_20_12.Management.Entity.RolesEntity;
import BT_20_12.Management.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.management.relation.Role;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    public RoleService roleService;

    @GetMapping("/form_role_table")
    public String role_table(Model model){
        model.addAttribute("notice_success",false);
        model.addAttribute("notice_error",false);
        model.addAttribute("role_information",roleService.getListRole());
        return "role-table";
    }
    @GetMapping("/form_add")
    public String form_add(){
        return "role-add";
    }
    @PostMapping("/add")
    public String add(@RequestParam("name_role")String name, @RequestParam("description") String description, Model model){
        boolean isSuccess =  roleService.saveNewRoles(name,description);
        if(isSuccess){
            model.addAttribute("isSuccess",isSuccess);
            return "role-add";
        }

        model.addAttribute("isFail",isSuccess);
        return "role-add";
    }

    @GetMapping("/delete/{id}")
    public String  deleteRole(@PathVariable int id,Model model){
        System.out.println(id);
        if(roleService.delete_Role(id)){
            model.addAttribute("notice_success",true);
            model.addAttribute("role_information",roleService.getListRole());
            return "role-table";
        }
        model.addAttribute("notice_success",false);
        model.addAttribute("notice_error",true);
        model.addAttribute("role_information",roleService.getListRole());
        return "role-table";
    }

    @GetMapping("/form_edit/{id}")
    public String form_edit(@PathVariable("id") int id,Model model){
        model.addAttribute("notice_success",false);
        model.addAttribute("notice_error",false);
        RolesEntity rolesEntity = roleService.findRoleByid(id);
        model.addAttribute("role_old",rolesEntity);
        return "role-edit";

    }
    @PostMapping("/edit")
    public String edit(@RequestParam("name_role")String name, @RequestParam("description") String description, Model model){
        RolesEntity rolesEntity = roleService.findRoleByName(name);
        if(rolesEntity == null){
            return "role-edit";
        }
        rolesEntity.setName(name);
        rolesEntity.setDescription(description);
        roleService.Save_role(rolesEntity);
        model.addAttribute("isSuccess",true);
        return "redirect:/role/form_edit/"+rolesEntity.getId();
    }
}
