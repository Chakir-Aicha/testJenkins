package com.example.gestion_rhbackend.mappers;

import com.example.gestion_rhbackend.dtos.UserDto;
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
    }

    @Test
    void fromUserToDto() {
    }
}