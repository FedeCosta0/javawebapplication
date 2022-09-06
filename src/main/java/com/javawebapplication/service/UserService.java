package com.javawebapplication.service;

import com.javawebapplication.domain.Authority;
import com.javawebapplication.domain.User;
import com.javawebapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
Service class for setting up the User before saving it
*/
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user) throws Exception {

        if (userRepository.existsUserByEmail(user.getEmail())) {
            throw new Exception(
                    "Email " + user.getEmail() + " taken");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUser(user);
        user.getAuthorities().add(authority);

        return userRepository.save(user);
    }

}
