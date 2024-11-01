package com.ayoub.project_management.service.message;

import com.ayoub.project_management.model.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long userId,Long projectId,String content) throws Exception;
    List<Message> getMessageByProjectId(Long projectId) throws Exception;
}
