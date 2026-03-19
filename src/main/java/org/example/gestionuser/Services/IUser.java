package org.example.gestionuser.Services;

import org.example.gestionuser.entities.User;

import java.util.List;

public interface IUser {
    List<User> getAllUsers();
    User updateUser (User user);
    User adduser (User user);
    User getUser (long idUser);
    void removeUser (long iduser);
    User findByEmail(String email);
}
