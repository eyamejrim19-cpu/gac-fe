package com.bna.gac.repositories;

import com.bna.gac.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Inbox: messages received by a user, newest first
    List<Message> findByToUser_IdOrderByCreatedAtDesc(Long toUserId);

    // Sent: messages sent by a user
    List<Message> findByFromUser_IdOrderByCreatedAtDesc(Long fromUserId);

    // Unread count for a user
    long countByToUser_IdAndIsReadFalse(Long toUserId);
}
