package com.javawebapplication.service;

import com.javawebapplication.domain.Request;
import com.javawebapplication.domain.User;
import com.javawebapplication.image_manager.FileUploadUtil;
import com.javawebapplication.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

/*
Service class for setting up the Request before saving it
*/
@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final FileUploadUtil fileUploadUtil;


    @Autowired
    public RequestService(RequestRepository requestRepository, FileUploadUtil fileUploadUtil) {
        this.requestRepository = requestRepository;
        this.fileUploadUtil = fileUploadUtil;
    }

    public Request save(Request request, User user, MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        request.setImageName(fileName);
        request.setUser(user);
        request.setAccepted(false);

        String uploadDir = "requests_images/" + user.getLastname() + "_" + user.getFirstname();

        fileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return requestRepository.save(request);
    }

    public RequestRepository getRequestRepository() {
        return requestRepository;
    }
}
