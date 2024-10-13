package com.example.gestion_rhbackend.services;

import com.example.gestion_rhbackend.dtos.UserDto;
import com.example.gestion_rhbackend.entities.User;
import org.springframework.stereotype.Service;

public interface UserManagementService{

    UserDto registre(UserDto userRequest);

    UserDto login(UserDto userRequest);
}
