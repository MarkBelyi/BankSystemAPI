package com.example.banksystemapi.Services;

import com.example.banksystemapi.Database.User;
import com.example.banksystemapi.Repository.UserRepository;
import com.example.banksystemapi.Responses.RegistrationResponse;
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

    public RegistrationResponse registerUser(String login, String password, String phone) {
        try {
            User newUser = new User();
            newUser.setLogin(login);
            newUser.setPassword(password);
            newUser.setPhone(phone);
            userRepository.save(newUser);
            return new RegistrationResponse("SUCCESS", "User registered successfully");
        } catch (DataIntegrityViolationException e) {
            return new RegistrationResponse("ERROR", "Login already exists");
        } catch (Exception e) {
            return new RegistrationResponse("ERROR", "Registration failed");
        }
    }

    public boolean checkIfUserExists(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
