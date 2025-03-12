package ch.login_project.login.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping()
public class WebController {

    @RequestMapping("home")
    public String home(){
        return "home";

    }
    @RequestMapping("")
    public String home2(){
        return "home";

    }



}
