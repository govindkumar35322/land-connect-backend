package com.landconnect.service;

public interface EmailTemplateService {
    String getWelcomeTemplate(String name);
    public String getBookingCreatedTemplate(
            String ownerName,
            String buyerName,
            String landTitle,
            String visitDate
    );
     String  getBookingApprovedTemplate(
             String buyerName,
             String ownerName,
             String landTitle,
             String visitDate
     );
    String getBookingRejectedTemplate(
            String buyerName,
            String ownerName,
            String landTitle,
            String visitDate
    );
    String getPasswordResetTemplate(String name,String resetLink);
}
