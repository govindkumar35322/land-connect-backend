package com.landconnect.service;

public interface MailService {
    void sendEmail(String to, String subject, String body);

    void sendHtmlEmail(String to, String subject, String htmlContent);
}
