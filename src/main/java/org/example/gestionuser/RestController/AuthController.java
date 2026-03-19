package org.example.gestionuser.RestController;

import lombok.AllArgsConstructor;
import org.example.gestionuser.auth.AuthFacade;
import org.example.gestionuser.dtos.LoginRequest;
import org.example.gestionuser.dtos.LoginResponse;
import org.example.gestionuser.dtos.TokenValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LoginResponse response = authFacade.login(request.getEmail(), request.getMotDePasse());

        if (response.getToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        TokenValidationResponse response = authFacade.validateAuthorizationHeader(authHeader);
        if (!response.isValid()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
