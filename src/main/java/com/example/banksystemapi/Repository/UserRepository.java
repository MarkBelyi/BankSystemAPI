package com.example.banksystemapi.Repository;

import com.example.banksystemapi.Database.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLoginAndPassword(String login, String password);


}
