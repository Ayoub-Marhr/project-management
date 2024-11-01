package com.ayoub.project_management.controller;

import com.ayoub.project_management.model.Chat;
import com.ayoub.project_management.model.Message;
import com.ayoub.project_management.model.User;
import com.ayoub.project_management.request.CreatRequestMessage;
import com.ayoub.project_management.service.message.MessageService;
import com.ayoub.project_management.service.project.ProjectService;
import com.ayoub.project_management.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreatRequestMessage requestMessage) throws Exception {
            User user = userService.findUserById(requestMessage.getSenderId());
            if (user == null) {
                throw new Exception("User not found");
            }
            Chat chat = projectService.getChatById(requestMessage.getProjectId()).getProject().getChat();
            if (chat == null) {
                throw new Exception("Chat not found");
            }
            Message message = messageService.sendMessage(requestMessage.getSenderId(), requestMessage.getProjectId(), requestMessage.getContent());
            return ResponseEntity.ok(message);
    }
    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long projectId)throws Exception {
        List<Message> messages =messageService.getMessageByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
