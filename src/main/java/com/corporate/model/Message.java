package com.corporate.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "message")
@Data
public class Message {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;

    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
    private int chat_id;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private int user_id;

    @Column(name = "time")
    private String time;

    @Column(name = "text")
    private String text;

    @Column(name = "status")
    private boolean status;

    @Column(name = "is_fixed")
    private boolean isFixed;

    @Column(name = "ref")
    private Integer ref;

    public Message() {
    }

    public Message(int chat, int user, String time, String text, int ref) {
        this.chat_id = chat;
        this.user_id = user;
        this.time = time;
        this.text = text;
        this.status = false;
        this.isFixed = false;
        this.ref = ref;
    }
}