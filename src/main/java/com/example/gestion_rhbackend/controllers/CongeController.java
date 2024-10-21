package com.example.gestion_rhbackend.controllers;

import com.example.gestion_rhbackend.dtos.CongeDto;
import com.example.gestion_rhbackend.entities.Conge;
import com.example.gestion_rhbackend.entities.User;
import com.example.gestion_rhbackend.mappers.CongeMapper;
import com.example.gestion_rhbackend.repositories.CongeRepository;
import com.example.gestion_rhbackend.repositories.UserRepository;
import com.example.gestion_rhbackend.services.CongeService;
import jakarta.security.auth.message.config.AuthConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CongeController {
    private CongeService congeService;
    private CongeMapper congeMapper;

    @PostMapping("/employe/create_conge")
    public ResponseEntity<CongeDto> createConge(@RequestBody CongeDto congeDTO) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        return ResponseEntity.ok(congeMapper.CongeToDto(congeService.createConge(congeDTO,email)));
    }

    @GetMapping("/rh/getNonValidatedConges")
    public ResponseEntity<List<CongeDto>> getCongesNonValides(){
        List<Conge> conges=congeService.getCongesByStatus("EN_COURS");
        List<CongeDto> congeDTOList = new ArrayList<>();
        for (Conge c:conges) {
            congeDTOList.add(congeMapper.CongeToDto(c));
        }
        return ResponseEntity.ok(congeDTOList);
    }

    @PutMapping("/employe/updateConge/{id}")
    public ResponseEntity<CongeDto> updateConge(@PathVariable Long id, @RequestBody CongeDto congeDTO) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        return ResponseEntity.ok(congeMapper.CongeToDto(congeService.updateConge(id,congeDTO,email)));
    }

    @PutMapping("/rh/acceptConge/{id}")
    public ResponseEntity<CongeDto> acceptConge(@PathVariable Long id){
       return ResponseEntity.ok(congeService.acceptConge(id));
    }

    @PutMapping("/rh/rejectConge/{id}")
    public ResponseEntity<CongeDto> rejectConge(@PathVariable Long id){
        return ResponseEntity.ok(congeService.rejectConge(id));
    }

    @GetMapping("/employe/getConge/{userId}")
    public ResponseEntity<List<CongeDto>> getCongesByUser(@PathVariable Long userId) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        List<Conge> conges = congeService.getCongesByUser(userId,email);
        List<CongeDto> congeDTOList = conges.stream()
                .map(congeMapper::CongeToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(congeDTOList);
    }

    @DeleteMapping("/employe/deleteConge/{id}")
    public void deleteConge(@PathVariable Long id) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        congeService.deleteConge(id,email);
    }
   /* @GetMapping
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
    }*/
}
