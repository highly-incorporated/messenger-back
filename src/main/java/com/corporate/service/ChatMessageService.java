package com.corporate.service;

import com.corporate.dto.FindChatMessagesDto;
import com.corporate.model.Message;
import com.corporate.model.User;
import com.corporate.repository.ChatMessageRepository;
import com.corporate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService {
    @Autowired private ChatMessageRepository repository;
    @Autowired private UserRepository userRepository;

    public Message save(Message chatMessage, int ref) {
        if (ref != -1) {
            chatMessage.setRef(ref);
        }
        repository.save(chatMessage);
        return chatMessage;
    }

    public FindChatMessagesDto findChatMessages(int chat_id) {
        List<Message> messages = (repository.getMessages(chat_id));
        List<User> users = new ArrayList<>();

        for (Message message: messages) {
            User user = userRepository.findById(message.getUser_id());
            user.setPassword("hidden");
            users.add(user);
        }


        return new FindChatMessagesDto(messages, users);
    }
}