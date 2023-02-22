package com.javawebapplication.controller;

import com.javawebapplication.domain.Request;
import com.javawebapplication.domain.User;
import com.javawebapplication.domain.Status;
import com.javawebapplication.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/request")
public class RequestController {
    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("")
    public String createRequestForm(ModelMap model) {
        model.put("request", new Request());
        return "request";
    }

    @PostMapping("")
    public String createRequest(@RequestParam("image") MultipartFile multipartFile, @AuthenticationPrincipal User user,
                                Request request) throws IOException {
        if (request.getDescription().isEmpty()) {
            return "redirect:/request/empty-description";
        }
        requestService.saveNewRequest(request, user, multipartFile);
        return "redirect:/requests";
    }

    @GetMapping("/list")
    public String requestsList(@AuthenticationPrincipal User user, ModelMap model) {
        Boolean isAdmin = user.isAdmin();
        Iterable<Request> requests;
        if (isAdmin) requests = requestService.findAll();
        else requests = requestService.findByUser(user);
        model.put("requests", requests);
        return "requests";
    }

    @GetMapping("/delete/{requestId}")
    public String deleteRequest(@PathVariable Long requestId, @AuthenticationPrincipal User user) {
        Optional<Request> requestToBeDeleted = requestService.findById(requestId);
        requestToBeDeleted.ifPresent(request -> requestService.deleteRequest(request, user));
        return "redirect:/request/list";
    }

    @GetMapping("/view/{requestId}")
    public String requestView(@PathVariable Long requestId, ModelMap model) {
        Optional<Request> requestOpt = requestService.findById(requestId);
        requestOpt.ifPresent(request -> model.put("request", request));
        return "requestView";
    }

    @GetMapping("/accept/{requestId}")
    public String acceptRequest(@PathVariable Long requestId) {
        Optional<Request> requestToBeAccepted = requestService.findById(requestId);
        if (requestToBeAccepted.isPresent()) {
            Request request = requestToBeAccepted.get();
            if (request.getStatus() == Status.REQUEST_PENDING) {
                request.setStatus(Status.REQUEST_ACCEPTED);
                requestService.update(request);
            }
        }
        return "redirect:/request/list";
    }

    @GetMapping("/advance_payment/{requestId}")
    public String advancePayment(@PathVariable Long requestId) {
        Optional<Request> optRequest = requestService.findById(requestId);
        if (optRequest.isPresent()) {
            Request request = optRequest.get();
            if (request.getStatus() == Status.REQUEST_ACCEPTED) {
                request.setStatus(Status.DRAWING_IN_PROGRESS);
                requestService.update(request);
            }
        }
        return "redirect:/request/list";
    }

    @GetMapping("/drawing_completed/{requestId}")
    public String drawingCompleted(@PathVariable Long requestId) {
        Optional<Request> optRequest = requestService.findById(requestId);
        if (optRequest.isPresent()) {
            Request request = optRequest.get();
            if (request.getStatus() == Status.DRAWING_IN_PROGRESS) {
                request.setStatus(Status.DRAWING_COMPLETED);
                requestService.update(request);
            }
        }
        return "redirect:/request/list";
    }

    @GetMapping("/drawing_review/{requestId}")
    public String drawingReview(@PathVariable Long requestId) {
        Optional<Request> optRequest = requestService.findById(requestId);
        if (optRequest.isPresent()) {
            Request request = optRequest.get();
            if (request.getStatus() == Status.DRAWING_COMPLETED) {
                request.setStatus(Status.DRAWING_IN_PROGRESS);
                requestService.update(request);
            }
        }
        return "redirect:/request/list";
    }

    @GetMapping("/make_appointment/{requestId}")
    public String makeAppointment(@PathVariable Long requestId) {
        Optional<Request> optRequest = requestService.findById(requestId);
        if (optRequest.isPresent()) {
            Request request = optRequest.get();
            if (request.getStatus() == Status.DRAWING_COMPLETED) {
                request.setStatus(Status.APPOINTMENT_MADE);
                requestService.update(request);
            }
        }
        return "redirect:/request/list";
    }

    @GetMapping("/empty-description")
    public String errorEmptyDescription() {
        return "empty-description";
    }

}
