package com.ayoub.project_management.service.invitation;

import com.ayoub.project_management.model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

    public void sendInvitation(String email, Long ProjectId) throws MessagingException;
    public Invitation acceptInvitation(String Token, Long userId) throws Exception;
    public String getTokenByUserEmail(String UserEmail);
    public void deleteToken(String Token);}

