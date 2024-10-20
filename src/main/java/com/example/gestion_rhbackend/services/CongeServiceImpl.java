package com.example.gestion_rhbackend.services;

import com.example.gestion_rhbackend.dtos.CongeDto;
import com.example.gestion_rhbackend.entities.Conge;
import com.example.gestion_rhbackend.repositories.CongeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CongeServiceImpl implements CongeService {
    @Autowired
    private CongeRepository congeRepository;
    @Override
    public Conge createConge(Conge c) {
        return congeRepository.save(c);
    }

    @Override
    public List<Conge> getAllConges() {
        return congeRepository.findAll();
    }

    @Override
    public Conge updateConge(Long id, CongeDto congeDTO) {
        // Vérifier si le congé existe
        Conge existingConge = congeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conge not found with id: " + id));
        existingConge.setStatus(congeDTO.getStatus());

        // Enregistrer les changements
        return congeRepository.save(existingConge);
    }

    @Override
    public List<Conge> getCongesByUser(Long userId) {

        return congeRepository.findCongesByUser_Id(userId);
    }
}
