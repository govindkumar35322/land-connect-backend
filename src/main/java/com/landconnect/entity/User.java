package com.landconnect.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Entity
@SuperBuilder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")

public class User extends BaseEntity  implements UserDetails {

    @Column(name="first_name",nullable = false)
    private String firstName;
    @Column(name="last_name",nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name="phone_number",nullable = false,unique=true)
    private String phoneNumber;
    private boolean enabled;
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles=new HashSet<>();
    @OneToMany(mappedBy="owner",cascade=CascadeType.ALL,orphanRemoval = true,fetch=FetchType.LAZY)
   private Set<Land> lands =new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
    private List<Favorite> favorites =new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL,orphanRemoval=true)
    private List<Booking> booking= new ArrayList<>();

}
