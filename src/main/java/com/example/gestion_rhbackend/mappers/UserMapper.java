package com.example.gestion_rhbackend.mappers;

import com.example.gestion_rhbackend.dtos.UserDto;
import com.example.gestion_rhbackend.entities.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User fromDtoToUser(UserDto userDto){
        User user=new User();
        BeanUtils.copyProperties(userDto,user);
        return user;
    }
    public UserDto fromUserToDto(User user){
        UserDto userDto=new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }
}