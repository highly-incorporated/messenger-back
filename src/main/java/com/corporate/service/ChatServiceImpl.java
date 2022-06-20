package com.corporate.service;

import com.corporate.dto.LastMessageDto;
import com.corporate.dto.ShowDto;
import com.corporate.model.Chat;
import com.corporate.model.ChatRef;
import com.corporate.model.Message;
import com.corporate.repository.ChatMessageRepository;
import com.corporate.repository.ChatRefRepository;
import com.corporate.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService{
    private final ChatRepository chatRepository;

    private final ChatRefRepository chatRefRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final UserService userService;

    @Autowired
    private LastMessageDto lastMessageDto;

    @Override
    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }

    public LastMessageDto showChats(int userId, String type){
        Collection chats = userService.getChats(userId);
        List<Chat> user_chats = chatRepository.getUserChats(type, chats);
        lastMessageDto.setChats(user_chats);
        ArrayList<Message> last_mes = new ArrayList();
        for (Chat chat: user_chats){
            last_mes.add(chatMessageRepository.getLastMessage(chat.getId()));
        }
        lastMessageDto.setMessages(last_mes);
        return lastMessageDto;
    }

    @Override
    public ChatRef save_ref(ChatRef chatRef){
        return chatRefRepository.save(chatRef);
    }

    @Override
    public int saveChannel(String name, int teamId) {
        if (chatRepository.checkChannel(name) != null) { return -1; }
        Chat chat = chatRepository.save(new Chat(name, teamId));
        return chat.getId();
    }

    @Override
    public ShowDto showChannels(int teamId) {
        return new ShowDto(chatRepository.getChatsId(teamId), chatRepository.getChatsName(teamId));
    }

    @Override
    public Message findMassageById(int id) {
        return chatMessageRepository.findMessageByMessageId(id);
    }

    @Override
    public void dePinedMessage(int id) {
        chatMessageRepository.dePinedMessage(id);
    }

    @Override
    public void pinMessage(int id) {
        chatMessageRepository.pinMessage(id);
    }

    @Override
    public List<ChatRef> getRef(int id) {
        return chatRefRepository.getRef(id);
    }
}