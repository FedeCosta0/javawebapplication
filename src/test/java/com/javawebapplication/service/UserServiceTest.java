package com.javawebapplication.service;

import com.javawebapplication.domain.Authority;
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
import static org.mockito.BDDMockito.willReturn;
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
        String password = "test_password";
        User user = new User("user@user.com", password, "Mario", "Rossi");
        when(userRepository.existsUserByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // when
        userService.save(user);

        // then
        assertThat(passwordEncoder.matches(password, user.getPassword())).isEqualTo(true);
    }

    @Test
    @DisplayName("Saving User: Authority correctly assigned")
    void isAuthorityCorrectlyAssigned() throws Exception {
        // given
        User user = new User("user@user.com", "password", "Mario", "Rossi");
        when(userRepository.existsUserByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        Authority expected_authority = new Authority();
        expected_authority.setAuthority("ROLE_USER");
        expected_authority.setUser(user);

        // when
        userService.save(user);

        // then
        assertThat(user.getAuthorities().contains(expected_authority)).isEqualTo(true);
    }

    @Test
    @DisplayName("Saving User: Right user provided to the repository")
    void isRightUserProvidedToRepository() throws Exception {
        // given
        User user = new User("user@user.com", "user", "Mario", "Rossi");

        // when
        userService.save(user);

        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    @DisplayName("Saving User: With taken email correctly throws exception")
    void saveNewUserWithEmailTaken() {
        // given
        User user = new User("user@user.com", "user", "Mario", "Rossi");
        willReturn(true).given(this.userRepository).existsUserByEmail(user.getEmail());

        // then
        assertThatThrownBy(() -> userService.save(user)).isInstanceOf(Exception.class)
                .hasMessageContaining("Email " + user.getEmail() + " taken");
        verify(userRepository, never()).save(any());
    }
}