package com.corporate.service;

import com.corporate.model.User;
import com.corporate.repository.ChatRepository;
import com.corporate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String getUsersId(String participants, String admin) {
        StringBuilder usersId = new StringBuilder(admin + " ");
        if (!participants.equals("")) {
            User user;
            for (String email : participants.split(" ")) {
                user = findByEmail(email);
                if (user == null || user.getId() == Integer.parseInt(admin)) {
                    return "";
                }
                usersId.append(user.getId() + " ");
            }
        }
        return usersId.toString();
    }

    @Override
    public String getUsersId(String participants, int id) {
        StringBuilder usersId = new StringBuilder();
        User user;
        for (String email : participants.split(" ")) {
            user = findByEmail(email);
            if (user == null || user.getId() == id) {
                return "";
            }
            usersId.append(user.getId() + " ");
        }
        return usersId.toString();
    }

    @Override
    public void addTeam(String teamId, String usersId) {
        int id;
        String teams;
        for (String userId: usersId.split(" ")) {
            id = Integer.parseInt(userId);
            teams = userRepository.findById(id).getTeams();
            if (teams == null) { teams = ""; }
            userRepository.updateTeams(teams + teamId + " ", id);
        }
    }

    @Override
    public List<Integer> getTeams(int userId) {
        List<Integer> teamsId = new ArrayList<>();
        String teams = userRepository.findById(userId).getTeams();
        if (teams == null) { return teamsId; }
        for (String id: teams.split(" ")) {
            teamsId.add(Integer.parseInt(id));
        }
        return teamsId;
    }

    @Override
    public void addChat(String chatId, String usersId) {
        int id;
        String chats;
        for (String userId: usersId.split(" ")) {
            id = Integer.parseInt(userId);
            chats = userRepository.findById(id).getChats();
            if (chats == null) { chats = ""; }
            userRepository.updateChats(chats + chatId + " ", id);
        }
    }

    @Override
    public List<Integer> getChats(int id) {
        List<Integer> chatsId = new ArrayList<>();
        String chats = userRepository.findById(id).getChats();
        if (chats == null) { return chatsId; }
        for (String chat_id: chats.split(" ")) {
            chatsId.add(Integer.parseInt(chat_id));
        }
        return chatsId;
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }
}
