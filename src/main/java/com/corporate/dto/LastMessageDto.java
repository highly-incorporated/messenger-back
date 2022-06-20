package com.corporate.dto;

import com.corporate.model.Chat;
import com.corporate.model.Message;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data @Component
public class LastMessageDto {
    private List<Chat> chats;
    private List<Message> messages;

    public LastMessageDto(List<Chat> chats, List<Message> messages) {
        this.chats = chats;
        this.messages = messages;
    }
}
