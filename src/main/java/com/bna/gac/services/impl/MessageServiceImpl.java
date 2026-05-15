package com.bna.gac.services.impl;

import com.bna.gac.dto.MessageDTO;
import com.bna.gac.entities.Message;
import com.bna.gac.entities.User;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.repositories.MessageRepository;
import com.bna.gac.repositories.UserRepository;
import com.bna.gac.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository    userRepository;

    @Override
    public MessageDTO send(Long fromUserId, MessageDTO dto) {
        User from = findUser(fromUserId);
        User to   = findUser(dto.getToUserId());

        Message msg = new Message();
        msg.setFromUser(from);
        msg.setToUser(to);
        msg.setSubject(dto.getSubject());
        msg.setBody(dto.getBody());
        msg.setEntityType(dto.getEntityType());
        msg.setEntityId(dto.getEntityId());

        return toDto(messageRepository.save(msg));
    }

    @Override
    public List<MessageDTO> getInbox(Long userId) {
        return messageRepository.findByToUser_IdOrderByCreatedAtDesc(userId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> getSent(Long userId) {
        return messageRepository.findByFromUser_IdOrderByCreatedAtDesc(userId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public MessageDTO markAsRead(Long messageId, Long userId) {
        Message msg = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
        if (msg.getToUser().getId().equals(userId)) {
            msg.setRead(true);
            messageRepository.save(msg);
        }
        return toDto(msg);
    }

    @Override
    public long countUnread(Long userId) {
        return messageRepository.countByToUser_IdAndIsReadFalse(userId);
    }

    @Override
    public List<MessageDTO> getAll() {
        return messageRepository.findAll().stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    private MessageDTO toDto(Message m) {
        MessageDTO dto = new MessageDTO();
        dto.setId(m.getId());
        dto.setFromUserId(m.getFromUser().getId());
        dto.setFromUsername(m.getFromUser().getUsername());
        dto.setFromNom(m.getFromUser().getNom());
        dto.setFromPrenom(m.getFromUser().getPrenom());
        dto.setToUserId(m.getToUser().getId());
        dto.setToUsername(m.getToUser().getUsername());
        dto.setSubject(m.getSubject());
        dto.setBody(m.getBody());
        dto.setEntityType(m.getEntityType());
        dto.setEntityId(m.getEntityId());
        dto.setRead(m.isRead());
        dto.setCreatedAt(m.getCreatedAt());
        return dto;
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
