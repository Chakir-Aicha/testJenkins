package com.example.gestion_rhbackend.controllers;

import com.example.gestion_rhbackend.dtos.CongeDto;
import com.example.gestion_rhbackend.entities.Conge;
import com.example.gestion_rhbackend.entities.User;
import com.example.gestion_rhbackend.mappers.CongeMapper;
import com.example.gestion_rhbackend.repositories.CongeRepository;
import com.example.gestion_rhbackend.repositories.UserRepository;
import com.example.gestion_rhbackend.services.CongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/conges")
public class CongeController {
    @Autowired
    private CongeService congeService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CongeRepository congeRepository;
    @Autowired
    private CongeMapper congeMapper;
    @PostMapping("/create")
    public ResponseEntity<CongeDto> createConge(@RequestBody CongeDto congeDTO) {
        if (congeDTO.getId_user() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        User user = userRepository.findById(congeDTO.getId_user())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Conge conge = congeMapper.DtotoConge(congeDTO, user);
        Conge createdConge = congeService.createConge(conge);
        CongeDto responseDTO = congeMapper.CongeToDto(createdConge);
        return ResponseEntity.ok(responseDTO);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CongeDto> updateConge(@PathVariable Long id, @RequestBody CongeDto congeDTO) {
        Conge updatedConge = congeService.updateConge(id, congeDTO);

        CongeDto responseDTO = congeMapper.CongeToDto(updatedConge);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping
    public ResponseEntity<List<CongeDto>> getAllConges() {
        List<Conge> conges = congeService.getAllConges();
        List<CongeDto> congeDTOList = conges.stream()
                .map(conge -> {
                    CongeDto congeDto = congeMapper.CongeToDto(conge);
                    if (conge.getUser() != null) {
                        congeDto.setId_user(conge.getUser().getId());
                    }
                    return congeDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(congeDTOList);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CongeDto>> getCongesByUser(@PathVariable Long userId) {
        List<Conge> conges = congeService.getCongesByUser(userId);
        List<CongeDto> congeDTOList = conges.stream()
                .map(congeMapper::CongeToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(congeDTOList);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteConge(@PathVariable Long id) {
        congeRepository.deleteById(id);
        return ResponseEntity.ok("Conge with ID " + id + " has been deleted successfully.");
    }
}
