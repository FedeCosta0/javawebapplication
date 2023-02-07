package com.javawebapplication.service;

import com.javawebapplication.domain.User;
import com.javawebapplication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("Saving User: password correctly encoded")
    void isPasswordCorrectlyEncoded() throws Exception {
        // given
        String email = "user@user.com";
        String raw_password = "test_password";
        String firstname = "Mario";
        String lastname = "Rossi";

        User user_to_be_saved = new User(email, raw_password, firstname, lastname);
        when(userRepository.existsUserByEmail(any())).thenReturn(false);

        // when
        User user = userService.save_user(user_to_be_saved);

        // then
        assertThat(passwordEncoder.matches(raw_password, user.getPassword())).isEqualTo(true);
    }

    @Test
    @DisplayName("Saving User: Authority correctly assigned")
    void isAuthorityCorrectlyAssigned() throws Exception {
        // given
        String email = "user@user.com";
        String raw_password = "test_password";
        String firstname = "Mario";
        String lastname = "Rossi";

        User user_to_be_saved = new User(email, raw_password, firstname, lastname);
        when(userRepository.existsUserByEmail(any())).thenReturn(false);

        // when
        User user = userService.save_user(user_to_be_saved);

        // then
        assertThat(user.getAuthorities().size()).isEqualTo(1);
        assertThat(user.getAuthorities().iterator().next().isUser()).isEqualTo(true);
    }

    @Test
    @DisplayName("Saving User: Right user provided to the repository")
    void isRightUserProvidedToRepository() throws Exception {
        // given
        String email = "user@user.com";
        String raw_password = "test_password";
        String firstname = "Mario";
        String lastname = "Rossi";

        User user_to_be_saved = new User(email, raw_password, firstname, lastname);
        when(userRepository.existsUserByEmail(any())).thenReturn(false);

        // when
        userService.save_user(user_to_be_saved);

        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user_to_be_saved);
    }

    @Test
    @DisplayName("Saving User: With taken email correctly throws exception")
    void saveNewUserWithEmailTaken() {
        // given
        String email = "user@user.com";
        String raw_password = "test_password";
        String firstname = "Mario";
        String lastname = "Rossi";

        User user_to_be_saved = new User(email, raw_password, firstname, lastname);
        when(userRepository.existsUserByEmail(any())).thenReturn(true);

        // then
        assertThatThrownBy(() -> userService.save_user(user_to_be_saved)).isInstanceOf(Exception.class)
                .hasMessageContaining("Email " + email + " taken");
        verify(userRepository, never()).save(any());
    }
}