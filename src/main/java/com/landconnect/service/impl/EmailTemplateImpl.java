package com.landconnect.service.impl;

import com.landconnect.service.EmailTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailTemplateImpl  implements EmailTemplateService {
    @Override
    public String getWelcomeTemplate(String name) {
        String html = loadTemplate("templates/email/welcome-email.html");

        return html.replace("{{name}}", name);
              
    }

    private String loadTemplate(String path) {
        try {

            ClassPathResource resource = new ClassPathResource(path);

            byte[] bytes = resource.getInputStream().readAllBytes();

            return new String(bytes, StandardCharsets.UTF_8);

        } catch (IOException e) {

            throw new RuntimeException("Unable to load email template: " + path, e);

        }
    }

    @Override
    public String getBookingCreatedTemplate(String ownerName, String buyerName, String landTitle, String visitDate) {
        String html = loadTemplate("templates/email/booking-created.html");

        html = html.replace("{{ownerName}}", ownerName);
        html = html.replace("{{buyerName}}", buyerName);
        html = html.replace("{{landTitle}}", landTitle);
        html = html.replace("{{visitDate}}", visitDate);

        return html;
    }

    @Override
    public String getBookingApprovedTemplate(String buyerName, String ownerName, String landTitle, String visitDate) {
        String html = loadTemplate("templates/email/booking-approved.html");
        html = html.replace("{{buyerName}}", buyerName);
        html = html.replace("{{ownerName}}", ownerName);
        html = html.replace("{{landTitle}}", landTitle);
        html = html.replace("{{visitDate}}", visitDate);

        return html;
    }

    @Override
    public String getBookingRejectedTemplate(String buyerName, String ownerName, String landTitle, String visitDate) {
        String html = loadTemplate("templates/email/booking-rejected.html");
        html = html.replace("{{buyerName}}", buyerName);
        html = html.replace("{{ownerName}}", ownerName);
        html = html.replace("{{landTitle}}", landTitle);
        html = html.replace("{{visitDate}}", visitDate);

        return html;
    }

    @Override
    public String getPasswordResetTemplate(String name, String resetLink) {
        String html = loadTemplate("templates/email/password-reset.html");

        html = html.replace("{{name}}", name);
        html = html.replace("{{resetLink}}", resetLink);

        return html;
    }
}
