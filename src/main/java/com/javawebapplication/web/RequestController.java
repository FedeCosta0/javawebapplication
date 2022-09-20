package com.javawebapplication.web;


import com.javawebapplication.domain.Request;
import com.javawebapplication.domain.User;
import com.javawebapplication.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class RequestController {
    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/request")
    public String createRequestForm(ModelMap model) {
        model.put("request", new Request());
        return "request";
    }

    @PostMapping("/request")
    public String createRequest(@RequestParam("image") MultipartFile multipartFile, @AuthenticationPrincipal User user,
                                Request request) throws IOException {
        if (request.getDescription().isEmpty()) {
            return "redirect:/request/empty-description";
        }
        requestService.create_request(request, user, multipartFile);
        return "redirect:/requests";
    }

    @GetMapping("/delete/{requestId}")
    public String deleteRequest(@PathVariable Long requestId, @AuthenticationPrincipal User user) {
        Optional<Request> optionalRequestToBeDeleted = requestService.findById(requestId);
        if (optionalRequestToBeDeleted.isPresent()) {
            Request requestToBeDeleted = optionalRequestToBeDeleted.get();
            user.removeRequest(requestToBeDeleted);
            requestToBeDeleted.setUser(null);
            requestService.deleteById(requestToBeDeleted.getId());
        }
        return "redirect:/requests";
    }

    @GetMapping("/requests/{requestId}")
    public String requestView(@PathVariable Long requestId, ModelMap model) {
        Optional<Request> requestOpt = requestService.findById(requestId);
        requestOpt.ifPresent(request -> model.put("request", request));
        return "requestView";
    }

    @GetMapping("/requests")
    public String requestsList(@AuthenticationPrincipal User user, ModelMap model) {
        Boolean isAdmin = user.isAdmin();
        Iterable<Request> requests;
        if (isAdmin) {
            requests = requestService.findAll();
        } else {
            requests = requestService.findByUser(user);
        }
        model.put("requests", requests);
        return "requests";
    }

    @GetMapping("/accept/{requestId}")
    public String acceptRequestAdmin(@PathVariable Long requestId) {
        Optional<Request> requestToBeAccepted = requestService.findById(requestId);
        if (requestToBeAccepted.isPresent()) {
            Request request = requestToBeAccepted.get();
            if (!request.getAccepted()) {
                request.setAccepted(Boolean.TRUE);
                requestService.save(request);
            }
        }
        return "redirect:/requests";
    }

    @GetMapping("/request/empty-description")
    public String errorEmptyDescription() {
        return "empty-description";
    }

}
