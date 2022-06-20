package com.corporate.rest;

import com.corporate.dto.*;
import com.corporate.exceptions.InvalidTokenExceptions;
import com.corporate.jwt.JWTProvider;
import com.corporate.model.*;
import com.corporate.service.ChatService;
import com.corporate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/chat/")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatIdDto chatIdDto;

    @PostMapping("create")
    public ResponseEntity createChat(HttpServletRequest request, @RequestBody CreateChatDto CreateChatDto) {

        if (!jwtProvider.validateAccessToken(request.getHeader("Authorization"))) {
            return ResponseEntity.badRequest().body("Unauthorized");
        }
        int team_id = 1;
        String type = CreateChatDto.getType();
        String participants = " ";
        String ava;
        if (type.equals("GROUP")){
            List<User> participants_list = CreateChatDto.getParticipants();
            participants = String.valueOf(userService.findByUsername(jwtProvider.getAccessClaims(request.getHeader("Authorization")).getSubject()).getId()).concat(" ");
            User user = userService.findByUsername(jwtProvider.getAccessClaims(request.getHeader("Authorization")).getSubject());
            String chats = user.getChats();
            for (int i = 0; i < participants_list.size(); i++){
                user = participants_list.get(i);
                String id = String.valueOf(user.getId()).concat(" ");
                participants = participants.concat(id);
            }
            ava = CreateChatDto.getAva();
        }
        else{
            participants = String.valueOf(userService.findByUsername(jwtProvider.getAccessClaims(request.getHeader("Authorization")).getSubject()).getId()).concat(" ");
            List<User> participants_list = CreateChatDto.getParticipants();
            User user = participants_list.get(0);
            ava = CreateChatDto.getAva();
            participants = participants.concat(String.valueOf(user.getId()));
        }
        String name = CreateChatDto.getName();
        chatIdDto.setId(chatService.saveChat(new Chat(name, participants, type, ava)).getId());
        userService.addChat(Integer.toString(chatIdDto.getId()), participants);
        chatService.save_ref(new ChatRef(chatIdDto.getId()));
        return ResponseEntity.ok(chatIdDto);
    }

    @PostMapping("showChats")
    public ResponseEntity ShowChats(HttpServletRequest request, @RequestBody ShowChatsDto showChatsDto) {

        if (!jwtProvider.validateAccessToken(request.getHeader("Authorization"))) {
            throw new InvalidTokenExceptions();
        }
        return ResponseEntity.ok(chatService.showChats(showChatsDto.getId(), showChatsDto.getChat_type()));
    }

    @PostMapping("createChannel")
    public ResponseEntity createChannel(HttpServletRequest request, @RequestBody CreateChannelDto createChannelDto) {

        if (!jwtProvider.validateAccessToken(request.getHeader("Authorization"))) {
            return ResponseEntity.badRequest().body("Unauthorized");
        }

        int channelId = chatService.saveChannel(createChannelDto.getName(), createChannelDto.getTeam_id());

        if (channelId == -1) {
            return ResponseEntity.notFound().build();
        }

        chatService.save_ref(new ChatRef(channelId));

        return ResponseEntity.ok().build();
    }

    @PostMapping("showChannels")
    public ResponseEntity showChannels(HttpServletRequest request, @RequestBody IdDto idDto) {

        if (!jwtProvider.validateAccessToken(request.getHeader("Authorization"))) {
            throw new InvalidTokenExceptions();
        }

        return ResponseEntity.ok(chatService.showChannels(idDto.getId()));
    }

    @PatchMapping("pinned")
    public ResponseEntity getPinned(HttpServletRequest request, @RequestBody PinMessageDto pinMessageDto) {

        if (!jwtProvider.validateAccessToken(request.getHeader("Authorization"))) {
            throw new InvalidTokenExceptions();
        }

        chatService.dePinedMessage(pinMessageDto.getChat_id());

        Message message = chatService.findMassageById(pinMessageDto.getMessage());

        if (message == null) {
            return ResponseEntity.notFound().build();
        }

        chatService.pinMessage(pinMessageDto.getMessage());

        return ResponseEntity.ok(message);
    }

    @PatchMapping("dePinned")
    public void dePinned(HttpServletRequest request, @RequestBody IdDto idDto) {

        if (!jwtProvider.validateAccessToken(request.getHeader("Authorization"))) {
            throw new InvalidTokenExceptions();
        }

        chatService.dePinedMessage(idDto.getId());
    }
    @GetMapping("getUser")
    public ResponseEntity getSelf(HttpServletRequest request) {

        if (!jwtProvider.validateAccessToken(request.getHeader("Authorization"))) {
            return ResponseEntity.badRequest().body("Unauthorized");
        }

        User user = userService.findById((int) jwtProvider.getAccessClaims(request.getHeader("Authorization")).get("id"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("getRef")
    public ResponseEntity getMedia(HttpServletRequest request, @RequestBody RefDto RefDto) {
        if (!jwtProvider.validateAccessToken(request.getHeader("Authorization"))) {
            throw new InvalidTokenExceptions();
        }
        int chatId = RefDto.getId();
        return ResponseEntity.ok(chatService.getRef(chatId));
    }
}