package com.corporate.repository;

import com.corporate.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "select * from message where chat_id = :chat_id", nativeQuery = true)
    List<Message> getMessages(@Param("chat_id") int chat_id);

    Message findMessageByMessageId(int message_id);

    @Query(value = "select * from message where chat_id = :chat_id ORDER BY message.message_id DESC LIMIT 1", nativeQuery = true)
    Message getLastMessage(@Param("chat_id") int chat_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE message SET is_fixed = 'false' WHERE chat_id = :chat_id AND is_fixed = 'true'", nativeQuery = true)
    void dePinedMessage(@Param("chat_id") int chat_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE message SET is_fixed = 'true' WHERE message_id = :message_id", nativeQuery = true)
    void pinMessage(@Param("message_id") int message_id);
}
