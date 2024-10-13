package com.example.gestion_rhbackend.services;


import com.example.gestion_rhbackend.entities.User;
import com.example.gestion_rhbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean updateUserPassword(Long id, String oldPassword, String newPassword) {
        User user = getUserById(id);
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    public User updateUserInfo(Long id, User updatedUser, byte[] picture) {
        User existingUser = getUserById(id);

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setLinkedIn(updatedUser.getLinkedIn());
        existingUser.setTwitter(updatedUser.getTwitter());

        if (picture != null) {
            existingUser.setPicture(picture); // Mettre à jour l'image en binaire
        }

        return userRepository.save(existingUser);
    }
}
