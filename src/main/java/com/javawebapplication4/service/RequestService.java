package com.javawebapplication4.service;

import com.javawebapplication4.domain.Request;
import com.javawebapplication4.domain.User;
import com.javawebapplication4.imagesmanager.FileUploadUtil;
import com.javawebapplication4.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class RequestService {
    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request save(Request request, User user, MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        request.setImage(fileName);
        request.setUser(user);
        request.setAccepted(false);

        String uploadDir = "request-images/" + user.getLastname() + "_" + request.getName();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return requestRepository.save(request);

    }

    public RequestRepository getRequestRepository() {
        return requestRepository;
    }
}
