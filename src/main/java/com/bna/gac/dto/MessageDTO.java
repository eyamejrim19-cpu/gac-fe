package com.bna.gac.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private Long          id;
    private Long          fromUserId;
    private String        fromUsername;
    private String        fromNom;
    private String        fromPrenom;
    private Long          toUserId;
    private String        toUsername;
    private String        subject;
    private String        body;
    private String        entityType;
    private Long          entityId;
    private boolean       read;
    private LocalDateTime createdAt;
}
