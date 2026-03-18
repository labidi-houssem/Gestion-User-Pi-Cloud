package org.example.gestionuser.RestController;

import lombok.AllArgsConstructor;
import org.example.gestionuser.Services.IUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final IUser iu;

}
