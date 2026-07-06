package com.landconnect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private boolean enabled;
    

}
