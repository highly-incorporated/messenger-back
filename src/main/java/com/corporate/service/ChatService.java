package com.corporate.service;

import com.corporate.dto.LastMessageDto;
import com.corporate.dto.ShowDto;
import com.corporate.model.*;

import java.util.List;

public interface ChatService {

    Chat saveChat(Chat chat);

    LastMessageDto showChats(int userId, String type);

    int saveChannel(String name, int teamId);

    ShowDto showChannels(int teamId);

    Message findMassageById(int id);

    ChatRef save_ref(ChatRef chatRef);

    void dePinedMessage(int id);

    void pinMessage(int id);

    List<ChatRef> getRef(int id);
}