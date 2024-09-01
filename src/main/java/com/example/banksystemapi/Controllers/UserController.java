package com.example.banksystemapi.Controllers;

import com.example.banksystemapi.DTO.UserRegistrationDto;
import com.example.banksystemapi.Database.User;
import com.example.banksystemapi.Requests.LoginRequest;
import com.example.banksystemapi.Responses.LoginResponse;
import com.example.banksystemapi.Services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        boolean isValidUser = userService.validateUser(loginRequest.getLogin(), loginRequest.getPassword());
        if (isValidUser) {
            return new LoginResponse("SUCCESS", "Login successful");
        } else {
            boolean userExists = userService.checkIfUserExists(loginRequest.getLogin());
            if (userExists) {
                return new LoginResponse("ERROR", "Incorrect password");
            } else {
                return new LoginResponse("ERROR", "User does not exist");
            }
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody UserRegistrationDto userDto) {
        if (userService.validateUser(userDto.getLogin(), userDto.getPassword())) {
            return "User already exists";
        }
        userService.registerUser(userDto.getLogin(), userDto.getPassword(), userDto.getPhone());
        return "User registered successfully";
    }


    @GetMapping("/all-users")
    public String getAllUsers() {
        List<User> users = userService.getAllUsers();
        if(users.isEmpty()){
            return "In this System you don't have users!";
        }else{
            return users.stream()
                    .map(user -> "ID: " + user.getId() + ", Login: " + user.getLogin() + ", Password: " + user.getPassword() + ", Phone: " + user.getPhone())
                    .collect(Collectors.joining("\n"));
        }
    }

}
