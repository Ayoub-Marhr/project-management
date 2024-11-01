package com.ayoub.project_management.service.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendEmailWithToken(String userEmail, String Link) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        String subject = "Join Project Team Invitation ";
        String text ="Click the link to join the project team"+Link;
        helper.setSubject(subject);
        helper.setText(text, true);
        helper.setTo(userEmail);
        try {
            mailSender.send(message);
        }catch (MailException e) {
            throw new MessagingException("Failed to send email");
        }

    }
}
