package com.example.gestion_rhbackend.services;

import com.example.gestion_rhbackend.dtos.CongeDto;
import com.example.gestion_rhbackend.entities.Conge;

import java.util.List;

public interface CongeService {

    Conge createConge(CongeDto congeDto, String email);

    List<Conge> getAllConges();

    Conge updateConge(Long id, CongeDto congeDTO, String email);

    CongeDto acceptConge(Long id);

    CongeDto rejectConge(Long id);


    List<Conge> getCongesByUser(String email);

    List<Conge> getCongesByStatus(String status);

    void deleteConge(Long id, String email);
}
