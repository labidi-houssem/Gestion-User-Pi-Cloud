package org.example.gestionuser.auth;

import lombok.AllArgsConstructor;
import org.example.gestionuser.Services.IUser;
import org.example.gestionuser.dtos.LoginResponse;
import org.example.gestionuser.dtos.SignupResponse;
import org.example.gestionuser.dtos.SignupStep1Request;
import org.example.gestionuser.dtos.SignupStep2Request;
import org.example.gestionuser.dtos.TokenValidationResponse;
import org.example.gestionuser.entities.StatutCompte;
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
            return new LoginResponse(null, null, null, email, null, null, false, "Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());
        String username = buildUsername(user);
        String role = user.getRole() != null ? user.getRole().name() : null;
        String statutCompte = user.getStatutCompte() != null ? user.getStatutCompte().name() : null;
        return new LoginResponse(token, user.getId(), username, user.getEmail(), role, statutCompte, false, "Login successful");
    }

    @Override
    public SignupResponse signupStep1(SignupStep1Request request) {
        if (request.getEmail() == null || request.getEmail().isBlank() ||
                request.getMotDePasse() == null || request.getMotDePasse().isBlank() ||
                request.getRole() == null) {
            return new SignupResponse(null, request.getEmail(), null, null, "Missing required fields");
        }

        User existing = userService.findByEmail(request.getEmail());
        if (existing != null) {
            return new SignupResponse(existing.getId(), existing.getEmail(),
                    existing.getRole() != null ? existing.getRole().name() : null,
                    existing.getStatutCompte() != null ? existing.getStatutCompte().name() : null,
                    "Email already exists");
        }

        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setMotDePasse(request.getMotDePasse());
        user.setRole(request.getRole());
        user.setStatutCompte(StatutCompte.EN_ATTENTE);

        User saved = userService.adduser(user);
        return new SignupResponse(saved.getId(), saved.getEmail(), saved.getRole().name(),
                saved.getStatutCompte().name(), "Step 1 completed. Continue with profile details.");
    }

    @Override
    public SignupResponse signupStep2(Long userId, SignupStep2Request request) {
        User user = userService.getUser(userId);
        if (user == null) {
            return new SignupResponse(null, null, null, null, "User not found");
        }

        if (request.getPhoto() != null) user.setPhoto(request.getPhoto());
        if (request.getTelephone() != null) user.setTelephone(request.getTelephone());
        if (request.getRegion() != null) user.setRegion(request.getRegion());

        if (request.getDiplomeExpert() != null) user.setDiplomeExpert(request.getDiplomeExpert());

        if (request.getVehicule() != null) user.setTypeVehicule(request.getVehicule());
        if (request.getCapacite() != null) user.setCapaciteKg(request.getCapacite());

        if (request.getAgence() != null) user.setAgence(request.getAgence());
        if (request.getCertificatTravail() != null) user.setCertificatTravail(request.getCertificatTravail());

        if (request.getAdresseCabinet() != null) user.setAdresseCabinet(request.getAdresseCabinet());
        if (request.getPresentationCarriere() != null) user.setPresentationCarriere(request.getPresentationCarriere());
        if (request.getTelephoneCabinet() != null) user.setTelephoneCabinet(request.getTelephoneCabinet());

        if (request.getNomOrganisation() != null) user.setNom_organisation(request.getNomOrganisation());
        if (request.getDescription() != null) user.setDescription(request.getDescription());

        User updated = userService.updateUser(user);
        return new SignupResponse(updated.getId(), updated.getEmail(),
                updated.getRole() != null ? updated.getRole().name() : null,
                updated.getStatutCompte() != null ? updated.getStatutCompte().name() : null,
                "Step 2 completed. Please verify your email.");
    }

    @Override
    public SignupResponse verifyEmail(Long userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return new SignupResponse(null, null, null, null, "User not found");
        }

        user.setStatutCompte(StatutCompte.APPROUVE);
        User updated = userService.updateUser(user);
        return new SignupResponse(updated.getId(), updated.getEmail(),
                updated.getRole() != null ? updated.getRole().name() : null,
                updated.getStatutCompte() != null ? updated.getStatutCompte().name() : null,
                "Email verified successfully. You can now log in.");
    }

    private String buildUsername(User user) {
        String nom = user.getNom() == null ? "" : user.getNom().trim();
        String prenom = user.getPrenom() == null ? "" : user.getPrenom().trim();
        String fullName = (nom + " " + prenom).trim();

        if (!fullName.isEmpty()) {
            return fullName;
        }

        return user.getEmail();
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

