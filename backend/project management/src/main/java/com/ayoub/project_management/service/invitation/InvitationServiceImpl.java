package com.ayoub.project_management.service.invitation;

import com.ayoub.project_management.Repository.InvitationRepository;
import com.ayoub.project_management.model.Invitation;
import com.ayoub.project_management.service.Email.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private EmailService emailService;


    @Override
    public void sendInvitation(String email, Long ProjectId) throws MessagingException {
            String invitationToken = UUID.randomUUID().toString();
            Invitation invitation = new Invitation();
            invitation.setEmail(email);
            invitation.setProjectId(ProjectId);
            invitation.setToken(invitationToken);

            invitationRepository.save(invitation);

            String invitationLink="http://localhost:5173/accept_invitation?token="+invitationToken;
            emailService.sendEmailWithToken(email,invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String Token, Long userId) throws Exception {
        Invitation invitation = invitationRepository.findByToken(Token);
        if(invitation==null){
            throw new Exception("Invalid Invitation token");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserEmail(String UserEmail) {
        Invitation invitation = invitationRepository.findByEmail(UserEmail);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String Token) {
        Invitation invitation = invitationRepository.findByToken(Token);
            invitationRepository.delete(invitation);
    }
}
