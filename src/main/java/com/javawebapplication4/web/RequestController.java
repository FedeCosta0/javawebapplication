package com.javawebapplication4.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.javawebapplication4.domain.Request;
import com.javawebapplication4.domain.User;
import com.javawebapplication4.repositories.RequestRepository;

@Controller
public class RequestController {
    private final RequestRepository requestRepository;
    private Request tmpRequest = new Request();

    @Autowired
    public RequestController(RequestRepository requestRepository){
        this.requestRepository = requestRepository;
    }


    @GetMapping("/requests/{requestId}")
    public String getRequest(@PathVariable Long requestId, ModelMap model, HttpServletResponse response) throws IOException {
        Optional<Request> requestOpt = requestRepository.findByIdWithUser(requestId);

        if (requestOpt.isPresent()) {
            Request request = requestOpt.get();
            model.put("request", request);
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Product with id " + requestId + " was not found");
            return "request";
        }

        return "request";
    }

    @GetMapping("/r/{requestName}")
    public String requestUserView (@PathVariable String requestName, ModelMap model) {
        if (requestName != null) {
            String decodedRequestName = URLDecoder.decode(requestName, StandardCharsets.UTF_8);
            Optional<Request> requestOpt = requestRepository.findByName(decodedRequestName);

            if (requestOpt.isPresent()) {
                model.put("request", requestOpt.get());
            }
        }
        return "requestUserView";
    }

    @PostMapping("/requests/{requestId}")
    public String saveRequest(@AuthenticationPrincipal User user, Request request) {

        request = requestRepository.save(request);

        return "redirect:/requests/"+request.getId();
    }

    @PostMapping("/requests")
    public String createRequest(@AuthenticationPrincipal User user) {
/*        Request request = new Request();

        request.setPublished(false);
        request.setUser(user);

        request = requestRepository.save(request);


        return "redirect:/requests/"+request.getId();*/

        return "requests";
    }
}