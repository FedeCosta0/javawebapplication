package com.javawebapplication4.web;

import com.javawebapplication4.domain.User;
import com.javawebapplication4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerGet(ModelMap model) {
        model.put("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(User user){
        userService.save(user);
        return "redirect:/login";
    }
}
