package com.example.gestion_rhbackend.mappers;

import com.example.gestion_rhbackend.dtos.UserDto;
import com.example.gestion_rhbackend.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private UserMapper userMapper;
    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }
    @Test
    void fromDtoToUser() {
        UserDto userDto= new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("securePassword");
        userDto.setLinkedIn("john_doe_linkedIn");
        userDto.setTwitter("john_doe_twitter");
        userDto.setRole("USER");
        User user = userMapper.fromDtoToUser(userDto);
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.getLinkedIn(), user.getLinkedIn());
        assertEquals(userDto.getTwitter(), user.getTwitter());
        assertEquals(userDto.getRole(), user.getRole());
    }

    @Test
    void fromUserToDto() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("securePassword");
        user.setLinkedIn("john_doe_linkedIn");
        user.setTwitter("john_doe_twitter");
        user.setRole("USER");
        UserDto userDto = userMapper.fromUserToDto(user);
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.getLinkedIn(), userDto.getLinkedIn());
        assertEquals(user.getTwitter(), userDto.getTwitter());
        assertEquals(user.getRole(), userDto.getRole());
    }
}