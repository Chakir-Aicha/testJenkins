package com.example.gestion_rhbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dateDebut;
    private String dateFin;
    private String motif;
    @Column(columnDefinition = "ENUM('EN_COURS', 'VALIDE', 'REFUSE') default 'EN_COURS'")
    private String status;
    @ManyToOne
    @JoinColumn(name = "id_user",nullable = false)
    private User user;
}
