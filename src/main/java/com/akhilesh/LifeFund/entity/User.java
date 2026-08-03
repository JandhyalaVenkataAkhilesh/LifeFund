package com.akhilesh.LifeFund.entity;

import com.akhilesh.LifeFund.enums.AuthProvider;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    /*
     * Google OAuth does not provide a phone number.
     * Therefore, keep this nullable.
     * Later, after Google login, we can ask the user
     * to complete their profile.
     */
    @Column(unique = true, length = 15)
    private String phoneNumber;

    /*
     * Google users won't have a password.
     * LOCAL users will have an encrypted password.
     */
    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(nullable = false)
    private Boolean accountNonLocked = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /*
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Campaign> campaigns;

    @OneToMany(mappedBy = "donor", fetch = FetchType.LAZY)
    private List<Donation> donations;
    */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}