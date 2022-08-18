package com.javawebapplication4.web;

import com.javawebapplication4.domain.Request;
import com.javawebapplication4.domain.User;
import com.javawebapplication4.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final RequestRepository requestRepository;

    @Autowired
    public DashboardController(RequestRepository requestRepository){
        this.requestRepository = requestRepository;
    }

    @GetMapping("/dashboard")
    public String dashboardGet(@AuthenticationPrincipal User user, ModelMap model){
        List<Request> requests = requestRepository.findByUser(user);
        model.put("requests", requests);
        return "dashboard";
    }
}
