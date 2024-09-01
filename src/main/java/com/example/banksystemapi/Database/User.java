package com.example.banksystemapi.Database;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;
    private String password;

    @Column(nullable = false)
    private String phone;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    public String getLogin(){return login;}
    public void setLogin(String login){this.login = login;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

}
