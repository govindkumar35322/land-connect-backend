package com.landconnect.service.impl;

import com.landconnect.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl  implements MailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

  @Async
    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message= new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
   @Async
    @Override
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
     try{
         MimeMessage message=mailSender.createMimeMessage();
         MimeMessageHelper helper=new MimeMessageHelper(message,true);
         helper.setFrom(fromEmail);
         helper.setTo(to);
         helper.setSubject(subject);
         helper.setText(htmlContent,true);
         mailSender.send(message);
     }
     catch(MessagingException e){
         throw new RuntimeException("Failed to send email",e);
     }
    }
}
