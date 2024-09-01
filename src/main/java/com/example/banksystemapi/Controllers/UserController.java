package com.example.banksystemapi.Controllers;

import com.example.banksystemapi.Requests.ForgotPasswordRequest;
import com.example.banksystemapi.Requests.RegistrationRequest;
import com.example.banksystemapi.Database.User;
import com.example.banksystemapi.Requests.LoginRequest;
import com.example.banksystemapi.Responses.ForgotPasswordResponse;
import com.example.banksystemapi.Responses.LoginResponse;
import com.example.banksystemapi.Responses.RegistrationResponse;
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

    @PostMapping("/forgot_pass")
    public ForgotPasswordResponse forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        boolean isSent = userService.sendResetCode(forgotPasswordRequest.getPhone());
        if (isSent) {
            return new ForgotPasswordResponse("SUCCESS", "Reset code sent successfully");
        } else {
            return new ForgotPasswordResponse("ERROR", "Phone number not found");
        }
    }

    @PostMapping("/verify_code")
    public ForgotPasswordResponse verifyCode(@RequestBody ForgotPasswordRequest forgotPasswordRequest, @RequestParam String code) {
        boolean isValid = userService.verifyResetCode(forgotPasswordRequest.getPhone(), code);
        if (isValid) {
            return new ForgotPasswordResponse("SUCCESS", "Code verified successfully");
        } else {
            return new ForgotPasswordResponse("ERROR", "Invalid code");
        }
    }

    @PostMapping("/register")
    public RegistrationResponse register(@RequestBody RegistrationRequest regRequest) {
        return userService.registerUser(regRequest.getLogin(), regRequest.getPassword(), regRequest.getPhone());
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
