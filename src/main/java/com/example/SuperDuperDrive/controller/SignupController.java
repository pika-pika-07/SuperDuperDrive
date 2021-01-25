package com.example.SuperDuperDrive.controller;

import com.example.SuperDuperDrive.model.User;
import com.example.SuperDuperDrive.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;
    public SignupController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String signup(@ModelAttribute User user, Model model ){
        return "signup";
    }

    @PostMapping()
    public String userSignup(@ModelAttribute User user, Model model) {
        model.addAttribute("user",new User());
        String error = null;
        if(userService.isUserNameTaken(user.getUsername())){
            model.addAttribute("signupError", "UserName Not Available");
        }
        else{
            int result = userService.signupUser(user);
            System.out.println(result);
            model.addAttribute("signupSuccess", true);
        }

        return "login";

    }

}

