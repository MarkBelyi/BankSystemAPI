package com.example.banksystemapi.Services;

import com.example.banksystemapi.Database.User;
import com.example.banksystemapi.Repository.UserRepository;
import com.example.banksystemapi.Responses.RegistrationResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final Map<String, String> phoneToCodeMap = new HashMap<>();
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validateUser(String login, String password){
        return userRepository.findUserByLoginAndPassword(login, password).isPresent();
    }

    public boolean checkIfUserExists(String login) {
        return userRepository.findByLogin(login).isPresent();
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

    public boolean sendResetCode(String phone) {
        Optional<User> user = userRepository.findByPhone(phone);
        if (user.isPresent()) {
            String resetCode = generateCode();
            phoneToCodeMap.put(phone, resetCode);
            // код по SMS (эмуляция)
            System.out.println("Reset code for " + phone + " is: " + resetCode);
            return true;
        }
        return false;
    }

    public boolean verifyResetCode(String phone, String code) {
        String storedCode = phoneToCodeMap.get(phone);
        return storedCode != null && storedCode.equals(code);
    }

    private String generateCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000)); // Генерация кода от 000000 до 999999
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
