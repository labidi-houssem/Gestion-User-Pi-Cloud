package org.example.gestionuser.auth;

import lombok.AllArgsConstructor;
import org.example.gestionuser.Services.IUser;
import org.example.gestionuser.dtos.LoginResponse;
import org.example.gestionuser.dtos.TokenValidationResponse;
import org.example.gestionuser.entities.User;
import org.example.gestionuser.util.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthFacadeImpl implements AuthFacade {

    private final IUser userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResponse login(String email, String motDePasse) {
        User user = userService.findByEmail(email);

        if (user == null || user.getMotDePasse() == null || !user.getMotDePasse().equals(motDePasse)) {
            return new LoginResponse(null, null, email, "Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());
        return new LoginResponse(token, user.getId(), user.getEmail(), "Login successful");
    }

    @Override
    public TokenValidationResponse validateAuthorizationHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new TokenValidationResponse(false, null, null, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            return new TokenValidationResponse(false, null, null, "Token is invalid or expired");
        }

        return new TokenValidationResponse(
                true,
                jwtTokenProvider.getUserIdFromToken(token),
                jwtTokenProvider.getEmailFromToken(token),
                "Token is valid"
        );
    }
}

