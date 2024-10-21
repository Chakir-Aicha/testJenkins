package com.example.gestion_rhbackend.services;

import com.example.gestion_rhbackend.entities.User;
import com.example.gestion_rhbackend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("Password123");
        user.setLinkedIn("john_doe");
        user.setTwitter("@john_doe");
    }
    @Test
    void getUserByIdTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.getUserById(1L);
        assertEquals(user, result);

    }
    @Test
    void getUserByIdTestUserDoesNotExist() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void updateUserPasswordTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean result = userService.updateUserPassword(1L, "Password123", "newPassword456");
        assertTrue(result);
        // Verify that the password was updated in the repository
        assertEquals("newPassword456", user.getPassword());
        verify(userRepository,times(1)).save(user);
    }
    @Test
    void updateUserPasswordEchecTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean result = userService.updateUserPassword(1L, "password123", "newPassword456");
        assertFalse(result);
        assertEquals("Password123", user.getPassword());
        verify(userRepository,never()).save(user);
    }

    @Test
    void updateUserInfoTest() {
        User updatedUser = new User();
        updatedUser.setEmail("new@example.com");
        updatedUser.setFirstName("NewFirstName");
        updatedUser.setLastName("NewLastName");
        updatedUser.setLinkedIn("newLinkedIn");
        updatedUser.setTwitter("newTwitter");

        // Mock le comportement de findById pour renvoyer l'utilisateur existant
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // Appeler la méthode
        User result = userService.updateUserInfo(1L, updatedUser, null);
        System.out.println(result.toString());
        // Vérifications des changements
        assertNull(result.getPicture()); // S'assurer que la picture est bien null
        assertEquals("new@example.com", result.getEmail());
        assertEquals("NewFirstName", result.getFirstName());
        assertEquals("NewLastName", result.getLastName());
        assertEquals("newLinkedIn", result.getLinkedIn());
        assertEquals("newTwitter", result.getTwitter());

        // Vérification que la méthode save a été appelée une fois
        verify(userRepository, times(1)).save(user);


        // Vérification de l'égalité de l'objet complet
        assertEquals(user, result);
    }
}