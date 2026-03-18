package org.example.gestionuser.Services;

import lombok.AllArgsConstructor;
import org.example.gestionuser.Repositories.UserRepo;
import org.example.gestionuser.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class UserServiceImp implements IUser{
    private UserRepo ur;
    @Override
    public List<User> getAllUsers() {
        return ur.findAll();
    }

    @Override
    public User updateUser(User user) {
        return ur.save(user);
    }
    @Override
    public User adduser(User user) {
        return ur.save(user);
    }

    @Override
    public User getUser (long iduser) {
        return ur.findById(iduser).get();
    }
    @Override
    public void removeUser(long iduser) {
        ur.deleteById(iduser);

    }

}
