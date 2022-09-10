package com.javawebapplication.service;

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

    public User create_user(User user) throws Exception {
        User user_to_be_saved = new User(user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname());

        if (userRepository.existsUserByEmail(user_to_be_saved.getEmail())) {
            throw new Exception(
                    "Email " + user_to_be_saved.getEmail() + " taken");
        }
        String encodedPassword = passwordEncoder.encode(user_to_be_saved.getPassword());
        user_to_be_saved.setPassword(encodedPassword);

        user_to_be_saved.addAuthority("ROLE_USER");
        userRepository.save(user_to_be_saved);
        return user_to_be_saved;
    }

}
