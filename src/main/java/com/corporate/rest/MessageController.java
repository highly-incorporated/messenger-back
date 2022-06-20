package com.corporate.rest;

import com.corporate.dto.SendMessageDto;
import com.corporate.exceptions.InvalidTokenExceptions;
import com.corporate.jwt.JWTProvider;
import com.corporate.model.Message;
import com.corporate.model.User;
import com.corporate.repository.ChatRefRepository;
import com.corporate.service.ChatMessageService;
import com.corporate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class MessageController {

    @Autowired private SimpMessagingTemplate messagingTemplate;
    @Autowired private ChatMessageService chatMessageService;
    @Autowired private ChatRefRepository chatRefRepository;
    @Autowired private UserService userService;
    @Autowired private JWTProvider jwtProvider;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message chatMessage) {
        User user = userService.findById(chatMessage.getUser_id());
        user.setPassword("hidden");
        SendMessageDto sendMessageDto = new SendMessageDto(chatMessage, user);

        Message saved = chatMessageService.save(chatMessage, -1);
        String text = chatMessage.getText();
        if (text.indexOf("@w2m") != -1){
            String ref = new String();
            int start = text.indexOf("h", text.indexOf("@w2m"));
            ref = text.substring(start, text.indexOf(" ", start));
            chatRefRepository.updateW2M(ref, chatMessage.getChat_id());
        }
        if (text.indexOf("@git") != -1){
            String ref = new String();
            int start = text.indexOf("h", text.indexOf("@git"));
            ref = text.substring(start, text.indexOf(" ", start));
            chatRefRepository.updateGit(ref, chatMessage.getChat_id());
        }
        if (text.indexOf("@meet") != -1){
            String ref = new String();
            int start = text.indexOf("h", text.indexOf("@meet"));
            ref = text.substring(start, text.indexOf(" ", start));
            chatRefRepository.updateMeeting(ref, chatMessage.getChat_id());
        }
        messagingTemplate.convertAndSendToUser(chatMessage.getChat_id() + "","queue/messages", sendMessageDto);
    }

    @GetMapping("/messages/{chat_id}")
    public ResponseEntity<?> findChatMessages (HttpServletRequest request, @PathVariable int chat_id) {
        return ResponseEntity.ok(chatMessageService.findChatMessages(chat_id));
    }
}