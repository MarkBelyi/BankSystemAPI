package com.example.banksystemapi.controllers;

import com.example.banksystemapi.DTO.UserRegistrationDto;
import com.example.banksystemapi.Database.User;
import com.example.banksystemapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public String info(){
        return "This is user";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password){
        boolean isValid = userService.validateUser(login, password);
        if(isValid){
            return "Login successful";
        }else{
            return "Invalid login or password";
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody UserRegistrationDto userDto) {
        if (userService.validateUser(userDto.getLogin(), userDto.getPassword())) {
            return "User already exists";
        }
        userService.registerUser(userDto.getLogin(), userDto.getPassword());
        return "User registered successfully";
    }

    @GetMapping("/all-users")
    public String getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream()
                .map(user -> "ID: " + user.getId() + ", Login: " + user.getLogin() + ", Password: " + user.getPassword())
                .collect(Collectors.joining("\n"));
    }

}
