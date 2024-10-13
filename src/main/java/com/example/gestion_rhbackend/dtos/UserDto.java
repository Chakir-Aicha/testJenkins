package com.example.gestion_rhbackend.dtos;

import com.example.gestion_rhbackend.entities.User;
import com.example.gestion_rhbackend.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;
    private String role;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private int statusCode;
    private String message;
}
