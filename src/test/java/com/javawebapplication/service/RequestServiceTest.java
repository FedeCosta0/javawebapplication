package com.javawebapplication.service;

import com.javawebapplication.domain.Request;
import com.javawebapplication.domain.User;
import com.javawebapplication.imagesutility.FileUploadUtil;
import com.javawebapplication.repository.RequestRepository;
import com.javawebapplication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class RequestServiceTest {

    @Mock
    private RequestRepository mockRequestRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RequestService requestService;


    @BeforeEach
    void setUp() {
        requestService = new RequestService(mockRequestRepository, mockUserRepository);
    }

    @Test
    @DisplayName("Saving Request: Right request provided to the repository")
    void isRightRequestProvidedToRepository() throws Exception {
        // given
        MockMultipartFile mockFile = new MockMultipartFile("mockfile", "mockfile_name.jpg", "image/png", "some png".getBytes());
        User user = new User("user@user.com", "password", "Mario", "Rossi");
        String description = "Request Description";
        Request request = new Request();
        request.setDescription(description);

        // when
        requestService.saveNewRequest(request, user, mockFile);

        // then
        ArgumentCaptor<Request> requestArgumentCaptor = ArgumentCaptor.forClass(Request.class);
        verify(mockRequestRepository).save(requestArgumentCaptor.capture());
        Request capturedRequest = requestArgumentCaptor.getValue();
        assertThat(capturedRequest.getDescription()).isEqualTo(description);
        assertThat(capturedRequest.getImageName()).isEqualTo(mockFile.getOriginalFilename());
        assertThat(capturedRequest.getUser()).isEqualTo(user);
    }

    @Test @DisplayName("Saving Request: Image correctly saved")
    void isImageCorrectlySaved() throws IOException {
        // given
        MockMultipartFile mockFile = new MockMultipartFile("mockfile", "mockfile_name.jpg", "image/png", "some png".getBytes());
        User user = new User("user@user.com", "password", "Mario", "Rossi");
        String description = "Request Description";
        Request request = new Request();
        request.setDescription(description);

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(mockFile.getOriginalFilename()));
        String uploadDir = "requests_images/" + user.getLastname() + "_" + user.getFirstname();
        MockedStatic<FileUploadUtil> dummyStatic = Mockito.mockStatic(FileUploadUtil.class);

        // when
        requestService.saveNewRequest(request, user, mockFile);

        // then
        ArgumentCaptor<String> captureUploadDirectory = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captureFileName = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MockMultipartFile> captureMultipartFile = ArgumentCaptor.forClass(MockMultipartFile.class);


        dummyStatic.verify(() -> FileUploadUtil.saveFile(captureUploadDirectory.capture(), captureFileName.capture(), captureMultipartFile.capture()));

        String capturedUploadDirectory = captureUploadDirectory.getValue();
        String capturedFileName = captureFileName.getValue();
        MockMultipartFile capturedMultipartFile = captureMultipartFile.getValue();

        assertThat(capturedUploadDirectory).isEqualTo(uploadDir);
        assertThat(capturedFileName).isEqualTo(fileName);
        assertThat(capturedMultipartFile).isEqualTo(mockFile);
    }
}