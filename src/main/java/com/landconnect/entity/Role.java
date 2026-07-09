package com.landconnect.entity;

import com.landconnect.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="roles")
public class Role  extends BaseEntity{
//    @Id
//    private Long id;
    @Column(nullable=false,unique=true,length=50)
    @Enumerated(EnumType.STRING)
    private RoleType name;
    @Column(length=255)
    private String description;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users=new HashSet<>();
}
