package com.javawebapplication4.web;


import com.javawebapplication4.domain.Request;
import com.javawebapplication4.domain.User;
import com.javawebapplication4.service.RequestService;
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
import java.util.List;
import java.util.Optional;

@Controller
public class RequestsController {
    private final RequestService requestService;

    @Autowired
    public RequestsController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/requests")
    public String requestsListUser(@AuthenticationPrincipal User user, ModelMap model) {
        List<Request> requests = requestService.getRequestRepository().findByUser(user);
        model.put("requests", requests);
        return "requests";
    }

    @GetMapping("/manage_requests")
    public String requestsListAdmin(ModelMap model) {
        Iterable<Request> requests = requestService.getRequestRepository().findAll();
        model.put("requests", requests);
        return "manageRequests";
    }

    @GetMapping("/request")
    public String createRequestForm(ModelMap model) {
        model.put("request", new Request());
        return "request";
    }

    @PostMapping("/request")
    public String createRequest(@RequestParam("image") MultipartFile multipartFile,
                                @AuthenticationPrincipal User user,
                                Request request) throws IOException {

        requestService.save(request, user, multipartFile);

        return "redirect:/requests";
    }

    @GetMapping("/requests/{requestId}")
    public String requestView(@PathVariable Long requestId, ModelMap model) {
        Optional<Request> requestOpt = requestService.getRequestRepository().findById(requestId);
        requestOpt.ifPresent(request -> model.put("request", request));
        return "requestView";
    }

    @PostMapping("/requests/{requestId}")
    public String deleteRequest(@PathVariable Long requestId) {
        Optional<Request> requestToBeDeleted = requestService.getRequestRepository().findById(requestId);
        Long idToBeDeleted = 0L;
        if (requestToBeDeleted.isPresent()) {
            idToBeDeleted = requestToBeDeleted.get().getId();
        }
        requestService.getRequestRepository().deleteById(idToBeDeleted);
        return "redirect:/requests";
    }


    @GetMapping("/accept/{requestId}")
    public String acceptRequestAdmin(@PathVariable Long requestId) {
        Optional<Request> requestToBeAccepted = requestService.getRequestRepository().findById(requestId);
        if (requestToBeAccepted.isPresent()) {
            Request request = requestToBeAccepted.get();
            request.setAccepted(Boolean.TRUE);
            requestService.getRequestRepository().save(request);
        }


        return "redirect:/manage_requests";
    }


}