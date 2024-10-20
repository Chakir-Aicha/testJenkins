package com.example.gestion_rhbackend.services;

import com.example.gestion_rhbackend.dtos.CongeDto;
import com.example.gestion_rhbackend.entities.Conge;

import java.util.List;

public interface CongeService {
    Conge createConge(Conge c);
    List<Conge> getAllConges();

    Conge updateConge(Long id, CongeDto congeDTO);

    List<Conge> getCongesByUser(Long userId);
}
