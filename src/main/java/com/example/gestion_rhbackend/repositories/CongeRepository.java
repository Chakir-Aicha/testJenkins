package com.example.gestion_rhbackend.repositories;

import com.example.gestion_rhbackend.entities.Conge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CongeRepository extends JpaRepository<Conge, Long>  {
    List<Conge> findCongesByUser_Id(Long idUser);
}
