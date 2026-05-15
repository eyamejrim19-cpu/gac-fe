package com.bna.gac.controllers;

import com.bna.gac.dto.MessageDTO;
import com.bna.gac.services.MessageService;
import com.bna.gac.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MessageController {

    private final MessageService messageService;
    private final UserService    userService;

    /** Send a message — authenticated users only */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MessageDTO> send(
            Authentication auth,
            @RequestBody MessageDTO dto) {
        Long fromId = userService.getByUsername(auth.getName()).getId();
        return ResponseEntity.ok(messageService.send(fromId, dto));
    }

    /** Get inbox for current user */
    @GetMapping("/inbox")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MessageDTO>> inbox(Authentication auth) {
        Long userId = userService.getByUsername(auth.getName()).getId();
        return ResponseEntity.ok(messageService.getInbox(userId));
    }

    /** Get sent messages for current user */
    @GetMapping("/sent")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MessageDTO>> sent(Authentication auth) {
        Long userId = userService.getByUsername(auth.getName()).getId();
        return ResponseEntity.ok(messageService.getSent(userId));
    }

    /** Count unread messages */
    @GetMapping("/unread-count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Long> unreadCount(Authentication auth) {
        Long userId = userService.getByUsername(auth.getName()).getId();
        return ResponseEntity.ok(messageService.countUnread(userId));
    }

    /** Mark a message as read */
    @PutMapping("/{id}/read")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MessageDTO> markAsRead(
            @PathVariable Long id,
            Authentication auth) {
        Long userId = userService.getByUsername(auth.getName()).getId();
        return ResponseEntity.ok(messageService.markAsRead(id, userId));
    }

    /** Admin: get all messages */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MessageDTO>> getAll() {
        return ResponseEntity.ok(messageService.getAll());
    }
}
