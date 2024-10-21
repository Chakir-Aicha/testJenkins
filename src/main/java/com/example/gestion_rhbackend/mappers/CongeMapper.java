package com.example.gestion_rhbackend.mappers;

import com.example.gestion_rhbackend.dtos.CongeDto;
import com.example.gestion_rhbackend.entities.Conge;
import com.example.gestion_rhbackend.entities.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CongeMapper {
    public Conge DtotoConge(CongeDto congeDto, User user) {
        Conge conge = new Conge();
        BeanUtils.copyProperties(congeDto, conge);
        conge.setUser(user);
        return conge;
    }
    public CongeDto CongeToDto(Conge conge){
        CongeDto congeDto = new CongeDto();
        BeanUtils.copyProperties(conge,congeDto);
        congeDto.setUserFirstName(conge.getUser().getFirstName());
        congeDto.setUserLastName(conge.getUser().getLastName());
        return congeDto;
    }
}
