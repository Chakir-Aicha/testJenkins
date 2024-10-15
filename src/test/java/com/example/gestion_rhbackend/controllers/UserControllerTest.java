package com.example.gestion_rhbackend.controllers;

import com.example.gestion_rhbackend.dtos.ChangePasswordRequest;
import com.example.gestion_rhbackend.dtos.UserDto;
import com.example.gestion_rhbackend.entities.User;
import com.example.gestion_rhbackend.services.UserManagementServiceImpl;
import com.example.gestion_rhbackend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserManagementServiceImpl usersManagementService;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setLinkedIn("john_doe");
        user.setTwitter("@john_doe");

        userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
    }

    @Test
    void registerUserTest() {
        when(usersManagementService.registre(userDto)).thenReturn(userDto);
        ResponseEntity<UserDto> response = userController.register(userDto);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
        verify(usersManagementService, times(1)).registre(userDto);
    }
    @Test
    void getUserInfoTest() {
        when(userService.getUserById(1L)).thenReturn(user);
        ResponseEntity<User> response = userController.getUserInfo(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).getUserById(1L);
    }
    @Test
    void getUserInfoUserNotFoundTest() {
        when(userService.getUserById(2L)).thenThrow(new RuntimeException("User not found"));

        ResponseEntity<User> response = userController.getUserInfo(2L);
        System.out.println(response.toString());

        assertEquals(404, response.getStatusCodeValue());
    }
    @Test
    void changePasswordTest() {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldPassword("Password123");
        request.setNewPassword("NewPassword456");

        when(userService.updateUserPassword(1L, "Password123", "NewPassword456")).thenReturn(true);

        ResponseEntity<String> response = userController.changePassword(1L, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Password updated successfully", response.getBody());
        verify(userService, times(1)).updateUserPassword(1L, "Password123", "NewPassword456");
    }
    @Test
    void changePasswordFailureTest() {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldPassword("Password123");
        request.setNewPassword("NewPassword456");

        when(userService.updateUserPassword(1L, "Password123", "NewPassword456")).thenReturn(false);

        ResponseEntity<String> response = userController.changePassword(1L, request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Failed to update password", response.getBody());
        verify(userService, times(1)).updateUserPassword(1L, "Password123", "NewPassword456");
    }
    @Test
    void loginUserTest() {
        when(usersManagementService.login(userDto)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.login(userDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
        verify(usersManagementService, times(1)).login(userDto);
    }

    @Test
    void loginUserFailureTest() {
        when(usersManagementService.login(userDto)).thenReturn(null);

        ResponseEntity<UserDto> response = userController.login(userDto);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
        verify(usersManagementService, times(1)).login(userDto);
    }
}