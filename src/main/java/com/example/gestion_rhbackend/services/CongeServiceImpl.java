package com.example.gestion_rhbackend.services;

import com.example.gestion_rhbackend.dtos.CongeDto;
import com.example.gestion_rhbackend.entities.Conge;
import com.example.gestion_rhbackend.entities.User;
import com.example.gestion_rhbackend.mappers.CongeMapper;
import com.example.gestion_rhbackend.repositories.CongeRepository;
import com.example.gestion_rhbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CongeServiceImpl implements CongeService {
    @Autowired
    private CongeRepository congeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CongeMapper congeMapper;
    @Override
    public Conge createConge(CongeDto congeDto, String email) {
        User user=userRepository.findUserByEmail(email).orElseThrow();
        Conge conge=congeMapper.DtotoConge(congeDto,user);
        conge.setStatus("EN_COURS");
        return congeRepository.save(conge);
    }

    @Override
    public List<Conge> getAllConges() {
        return congeRepository.findAll();
    }

    @Override
    public Conge updateConge(Long id, CongeDto congeDTO, String email) {
        User user=userRepository.findUserByEmail(email).orElseThrow();
        Conge existingConge = congeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conge not found with id: " + id));
        if (existingConge.getUser().getId()==user.getId()) {
            // Modifier juste les champs qui sont présents dans le DTO
            if (congeDTO.getStatus() != null && !congeDTO.getStatus().equals("En cours")) {
                existingConge.setStatus(congeDTO.getStatus());
            }
            if (congeDTO.getDateDebut() != null) {
                existingConge.setDateDebut(congeDTO.getDateDebut());
            }
            if (congeDTO.getDateFin() != null) {
                existingConge.setDateFin(congeDTO.getDateFin());
            }
            if (congeDTO.getMotif() != null) {
                existingConge.setMotif(congeDTO.getMotif());
            }
        }
        return congeRepository.save(existingConge);
    }

    @Override
    public CongeDto acceptConge(Long id){
        Conge conge=congeRepository.findById(id).orElseThrow();
        conge.setStatus("VALIDE");
        return congeMapper.CongeToDto(congeRepository.save(conge));
    }
    @Override
    public CongeDto rejectConge(Long id){
        Conge conge=congeRepository.findById(id).orElseThrow();
        conge.setStatus("REFUSE");
        return congeMapper.CongeToDto(congeRepository.save(conge));
    }
    @Override
    public List<Conge> getCongesByUser(String email) {
        User user=userRepository.findUserByEmail(email).orElseThrow();
        return congeRepository.findCongesByUser(user);
    }
    @Override
    public List<Conge> getCongesByStatus(String status){
        return congeRepository.findCongesByStatus(status);
    }

    @Override
    public void deleteConge(Long id, String email) {
        User user=userRepository.findUserByEmail(email).orElseThrow();
        Conge conge=congeRepository.findById(id).orElseThrow();
        if (conge.getUser().getId() == user.getId()) congeRepository.deleteById(id);
    }
}