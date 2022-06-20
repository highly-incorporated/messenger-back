package com.corporate.dto;

import com.corporate.model.Message;
import com.corporate.model.User;
import lombok.Data;

@Data
public class SendMessageDto {
    private Message message;
    private User user;

    public SendMessageDto(Message message, User user) {
        this.message = message;
        this.user = user;
    }
}