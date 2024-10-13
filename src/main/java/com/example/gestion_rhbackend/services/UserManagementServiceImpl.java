package com.example.gestion_rhbackend.services;

import com.example.gestion_rhbackend.dtos.UserDto;
import com.example.gestion_rhbackend.entities.User;
import com.example.gestion_rhbackend.enums.RoleEnum;
import com.example.gestion_rhbackend.mappers.UserMapper;
import com.example.gestion_rhbackend.repositories.UserRepository;
import com.example.gestion_rhbackend.security.JWTUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class UserManagementServiceImpl implements UserManagementService{
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtils jwtUtils;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private UserMapper userMapper;
    @Override
    public UserDto registre(UserDto userRequest){
        User user=new User();
        UserDto userDto=new UserDto();
        try {
            user.setEmail(userRequest.getEmail());
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setRole(userRequest.getRole().toUpperCase());
            user.setPhoto(userRequest.getPhoto());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            userDto = userMapper.fromUserToDto(userRepository.save(user));
            if (user.getId() > 0) {
                userDto.setStatusCode(200);
                userDto.setMessage("user added successfully");
            }
        }catch (Exception e){
            userDto.setStatusCode(500);
            userDto.setMessage(e.getMessage());
        }
        return userDto;
    }
    @Override
    public UserDto login(UserDto userRequest){
        UserDto userDto=new UserDto();
        try{
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail()
                            ,userRequest.getPassword()));
            User user=userRepository.findUserByEmail(userRequest.getEmail()).orElseThrow();
            String jwt=jwtUtils.generateToken(user);
            String refreshToken= jwtUtils.generateRefreshToken(new HashMap<>(), user);
            userDto.setToken(jwt);
            userDto.setRefreshToken(refreshToken);
            userDto.setExpirationTime("24Hrs");
            userDto.setStatusCode(200);
            userDto.setRole(user.getRole().toUpperCase());
            userDto.setMessage("user logged in successfully");
        }catch (Exception e){
            userDto.setStatusCode(500);
            userDto.setMessage(e.getMessage());
        }
        return userDto;
    }
}
