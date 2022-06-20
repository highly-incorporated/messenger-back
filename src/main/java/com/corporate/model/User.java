package com.corporate.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@Component
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "teams")
    private String teams;

    @Column(name = "chats")
    private String chats;

    @Column(name = "avatar")
    private String avatar;

    public User() {

    }

    public User(String email, String password, String username, String avatar) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.avatar = avatar;
    }
}