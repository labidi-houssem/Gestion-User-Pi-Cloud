package org.example.gestionuser.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.gestionuser.entities.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupStep1Request {
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private Role role;
}
