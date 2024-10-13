package com.example.gestion_rhbackend.controllers;

import com.example.gestion_rhbackend.dtos.ChangePasswordRequest;
import com.example.gestion_rhbackend.dtos.UserDto;
import com.example.gestion_rhbackend.entities.User;
import com.example.gestion_rhbackend.services.UserManagementServiceImpl;
import com.example.gestion_rhbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserManagementServiceImpl usersManagementService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto reg) {
        logger.info("Registering user: {}", reg);
        UserDto registeredUser = usersManagementService.registre(reg);
        if (registeredUser != null) {
            return ResponseEntity.ok(registeredUser);
        } else {
            return ResponseEntity.badRequest().body(null); // Modify as needed for better error handling
        }
    }

    @GetMapping("/employe/{id}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            logger.warn("User not found for id: {}", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("Retrieved user: {}", user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/employe/{id}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest changePasswordRequest) {

        boolean isUpdated = userService.updateUserPassword(
                id,
                changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword()
        );

        if (isUpdated) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update password");
        }
    }

    @PutMapping("/employe/{id}/update-info")
    public ResponseEntity<User> updateUserInfo(
            @PathVariable Long id,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "linkedIn", required = false) String linkedIn,
            @RequestParam(value = "twitter", required = false) String twitter,
            @RequestParam(value = "picture", required = false) MultipartFile pictureFile) {

        User updatedUser = new User();
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setEmail(email);
        updatedUser.setLinkedIn(linkedIn);
        updatedUser.setTwitter(twitter);

        byte[] picture = null;
        if (pictureFile != null && !pictureFile.isEmpty()) {
            try {
                picture = pictureFile.getBytes();
            } catch (Exception e) {
                logger.error("Error converting file to byte array", e);
                return ResponseEntity.badRequest().body(null); // Improve error response
            }
        }

        User savedUser = userService.updateUserInfo(id, updatedUser, picture);
        if (savedUser == null) {
            logger.warn("Failed to update user info for id: {}", id);
            return ResponseEntity.badRequest().build(); // Provide meaningful error message
        }
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto req) {
        UserDto loggedUser = usersManagementService.login(req);
        if (loggedUser != null) {
            return ResponseEntity.ok(loggedUser);
        } else {
            return ResponseEntity.badRequest().body(null); // Improve error handling
        }
    }
}
