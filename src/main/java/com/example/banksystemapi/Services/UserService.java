package com.example.banksystemapi.Services;

import com.example.banksystemapi.Database.User;
import com.example.banksystemapi.Repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validateUser(String login, String password){
        return userRepository.findUserByLoginAndPassword(login, password).isPresent();
    }

    public void registerUser(String login, String password, String phone) {
        try {
            User newUser = new User();
            newUser.setLogin(login);
            newUser.setPassword(password);
            newUser.setPhone(phone);
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Login already exists");
        }
    }



    public boolean checkIfUserExists(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
