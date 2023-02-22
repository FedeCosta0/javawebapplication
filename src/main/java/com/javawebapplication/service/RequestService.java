package com.javawebapplication.service;

import com.javawebapplication.domain.Request;
import com.javawebapplication.domain.User;
import com.javawebapplication.imagesutility.FileUploadUtil;
import com.javawebapplication.repository.RequestRepository;
import com.javawebapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.domain.PageRequest.of;

/*
Service class for setting up the Request before saving it
*/
@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    public Request saveNewRequest(Request request, User user, MultipartFile multipartFile) throws IOException {
        String imageName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        request.setImageName(imageName);
        Optional<User> optUser = userRepository.findByIdWithRequests(user.getId());
        if (optUser.isPresent())
            user = optUser.get();
        user.addRequest(request);
        requestRepository.save(request);
        String uploadDirectory = "requests_images/" + user.getLastname() + "_" + user.getFirstname();
        FileUploadUtil.saveFile(uploadDirectory, imageName, multipartFile);

        return request;
    }

    public Boolean deleteRequest(Request request, User user) {
        Optional<User> optUser = userRepository.findByIdWithRequests(user.getId());
        if (optUser.isPresent())
            user = optUser.get();
        user.removeRequest(request);
        requestRepository.deleteById(request.getId());
        return Boolean.TRUE;
    }

    public Optional<Request> findById(Long id) {
        return requestRepository.findById(id);
    }

    public Iterable<Request> findAll() {
        return requestRepository.findAll();
    }

    public Collection<Request> list(int limit) {
        return requestRepository.findAll(of(0, limit)).toList();
    }

    public List<Request> findByUser(User user) {
        return requestRepository.findByUser(user);
    }

    public Request update(Request request) {
        return requestRepository.save(request);
    }


}
