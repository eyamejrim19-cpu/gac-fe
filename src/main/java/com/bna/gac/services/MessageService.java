package com.bna.gac.services;

import com.bna.gac.dto.MessageDTO;
import java.util.List;

public interface MessageService {
    MessageDTO send(Long fromUserId, MessageDTO dto);
    List<MessageDTO> getInbox(Long userId);
    List<MessageDTO> getSent(Long userId);
    MessageDTO markAsRead(Long messageId, Long userId);
    long countUnread(Long userId);
    List<MessageDTO> getAll(); // Admin only
}
