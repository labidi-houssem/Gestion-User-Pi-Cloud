package org.example.gestionuser.Repositories;

import org.example.gestionuser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
