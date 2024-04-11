package BT_20_12.Management.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class pageController {
    @GetMapping("/404_error")
    public String form(){
        return "404";
    }

    @GetMapping("/black")
    public String form_black(){
        return "blank";
    }

}
