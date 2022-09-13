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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/*
Service class for setting up the Request before saving it
*/
@Service
public class RequestService {
    private final RequestRepository requestRepository;


    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;

    }

    public Request create_request(Request request, User user, MultipartFile multipartFile) throws IOException {
        Request request_to_be_saved = new Request(request);
        request.erase();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        request_to_be_saved.setImageName(fileName);
        user.addRequest(request_to_be_saved);

        String uploadDir = "requests_images/" + user.getLastname() + "_" + user.getFirstname();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        requestRepository.save(request_to_be_saved);
        return request_to_be_saved;
    }

    public void deleteById(Long id) {
        requestRepository.deleteById(id);
    }

    public Optional<Request> findById(Long id) {
        return requestRepository.findById(id);
    }

    public Iterable<Request> findAll() {
        return requestRepository.findAll();
    }

    public List<Request> findByUser(User user) {
        return requestRepository.findByUser(user);
    }

    public Request save(Request request) {
        return requestRepository.save(request);
    }

}
