package com.corporate.dto;

import com.corporate.model.Message;
import com.corporate.model.User;
import lombok.Data;

import java.util.List;

@Data
public class FindChatMessagesDto {
    private List<Message> messages;
    private List<User> users;

    public FindChatMessagesDto(List<Message> messages, List<User> users) {
        this.messages = messages;
        this.users = users;
    }
}