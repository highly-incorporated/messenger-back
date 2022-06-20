package com.corporate.dto;

import com.corporate.model.User;
import lombok.Data;

import java.util.List;

@Data
public class CreateChatDto {
    private String name;
    private List<User> participants;
    private String type;
    private String ava;
}