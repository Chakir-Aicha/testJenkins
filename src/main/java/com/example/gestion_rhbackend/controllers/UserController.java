package com.example.gestion_rhbackend.controllers;

import com.example.gestion_rhbackend.dtos.UserDto;
import com.example.gestion_rhbackend.services.UserManagementServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private UserManagementServiceImpl usersManagementService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserDto> regeister(@RequestBody UserDto reg){
        return ResponseEntity.ok(usersManagementService.registre(reg));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto req){
        return ResponseEntity.ok(usersManagementService.login(req));
    }
}
