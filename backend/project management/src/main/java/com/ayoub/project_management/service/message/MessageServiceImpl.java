package com.ayoub.project_management.service.message;

import com.ayoub.project_management.Repository.MessageRepository;
import com.ayoub.project_management.Repository.UserRepository;
import com.ayoub.project_management.model.Chat;
import com.ayoub.project_management.model.Message;
import com.ayoub.project_management.model.User;
import com.ayoub.project_management.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;
    @Override
    public Message sendMessage(Long userId, Long projectId, String content) throws Exception {
        User sender = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();
        message.setSender(sender);
        message.setChat(chat);
        message.setCreatedAt(LocalDateTime.now());
        message.setContent(content);
        Message savedMessage = messageRepository.save(message);
        chat.getMessages().add(savedMessage);
        return savedMessage;

    }

    @Override
    public List<Message> getMessageByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getProjectById(projectId).getChat();
        List<Message> findChatById= messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
        return findChatById;
    }
}
