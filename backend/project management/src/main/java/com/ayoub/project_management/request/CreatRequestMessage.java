package com.ayoub.project_management.request;

import lombok.Data;

@Data
public class CreatRequestMessage {
    private Long senderId;
    private String content;
    private Long projectId;
}
