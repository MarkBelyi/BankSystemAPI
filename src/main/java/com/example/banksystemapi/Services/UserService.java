package com.example.banksystemapi.Services;

import com.example.banksystemapi.Database.User;
import com.example.banksystemapi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    public boolean validateUser(String login, String password){
        return userRepository.findUserByLoginAndPassword(login, password).isPresent();
    }

    public User registerUser(String login, String password) {
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(password);
        return userRepository.save(newUser);
    }

    // Метод для получения всех пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
