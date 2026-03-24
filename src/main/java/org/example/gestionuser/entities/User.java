package org.example.gestionuser.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String photo;
    private String email;
    private String motDePasse;
    private String telephone;
    private LocalDate dateCreation;
    @Enumerated(EnumType.STRING)
    private StatutCompte statutCompte;
    private String motifRefus;
    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isOnline;
    private LocalDateTime lastSeen;

    private String region;
    // =============================
    //  Expert
    // =============================

    private String diplomeExpert;
    // =============================
    //  Transporteur
    // =============================
    private String typeVehicule;
    private Double capaciteKg;
    private String numeroPlaque;
    private String Region;
    
    // =============================
    //  Agent
    // =============================

    private String agence;
    private String certificatTravail;
    // =============================
    //  veto
    // =============================
    private String adresseCabinet;
    private String presentationCarriere;
    private String telephoneCabinet;
    // =============================
    //  Organisateur
    // =============================
    private String nomOrganisation;
    private String nom_organisation;
    private String logo_organisation;
    private int cin;
    private String description;
    @Column(columnDefinition = "TEXT")
    private String description;
}


