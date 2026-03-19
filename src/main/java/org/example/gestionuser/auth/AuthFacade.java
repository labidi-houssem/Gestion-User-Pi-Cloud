package org.example.gestionuser.auth;

import org.example.gestionuser.dtos.LoginResponse;
import org.example.gestionuser.dtos.TokenValidationResponse;

public interface AuthFacade {
    LoginResponse login(String email, String motDePasse);
    TokenValidationResponse validateAuthorizationHeader(String authHeader);
}

