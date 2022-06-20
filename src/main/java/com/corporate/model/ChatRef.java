package com.corporate.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "chat_ref")
@Data
public class ChatRef {
    private static int newId = 0;

    @Id
    @Column(name = "ref_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "w2m")
    private String w2m;

    @Column(name = "git")
    private String  git;

    @Column(name = "meeting")
    private String meeting;

    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
    private int chat_id;

    public ChatRef() {

    }
    public ChatRef(int chat) {
        this.chat_id = chat;
    }
}
