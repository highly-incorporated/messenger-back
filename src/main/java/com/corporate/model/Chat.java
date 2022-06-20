package com.corporate.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "chat")
@Data
public class Chat {
    private static int newId = 0;

    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "chat_name")
    private String name;

    @Column(name = "chat_participants")
    private String participants;

    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private int teamId;

    @Column(name = "chat_type")
    private String chatType;

    @Column(name = "avatar")
    private String ava;

    public Chat() {
    }

    public Chat(String name, String participants, String type, String ava) {
        this.name = name;
        this.participants = participants;
        this.chatType = type;
        this.ava = ava;
        this.teamId = 0;
    }

    public Chat(String name, int teamId) {
        this.name = name;
        this.teamId = teamId;
        this.chatType = "Channel";
    }
}