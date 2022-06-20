package com.corporate.repository;

import com.corporate.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    Chat findById(int chat_id);

    @Query(value = "select * from chat where chat_type = :chat_type", nativeQuery = true)
    List<Chat> getChats(@Param("chat_type") String chat_type);

    @Query(value = "SELECT chat_name FROM chat WHERE chat_name = :name LIMIT 1", nativeQuery = true)
    String checkChannel(@Param("name") String name);

    @Query(value = "SELECT chat_id FROM chat WHERE team_id = :id", nativeQuery = true)
    List<Integer> getChatsId(@Param("id") int id);

    @Query(value = "SELECT chat_name FROM chat WHERE team_id = :id", nativeQuery = true)
    List<String> getChatsName(@Param("id") int id);

    @Query(value = "select * from chat where chat.chat_id in :chat_id and chat.chat_type = :chat_type", nativeQuery = true)
    List<Chat> getUserChats(@Param("chat_type") String chat_type, @Param("chat_id") Collection chat_id);
}