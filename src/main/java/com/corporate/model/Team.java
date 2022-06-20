package com.corporate.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "team")
@Data
public class Team {
    private static int newId = 0;

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "team_name")
    private String name;

    @Column(name = "team_participants")
    private String participants;

    @Column(name = "admins")
    private String admins;

    @Column(name = "avatar")
    private String avatar;

    public Team() {

    }

    public Team(String name, String participants, String admins, String avatar) {
        this.name = name;
        this.participants = participants;
        this.admins = admins;
        this.avatar = avatar;
    }
}