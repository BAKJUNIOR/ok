package com.esantefutur.esantefutur.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username" , unique = true , nullable = false)
    private String username;
    @Column(name = "password" , nullable = false)
    private String password;
    @Column(name = "email" , unique = true , nullable = false)
    private String email;

    @Column(name = "date_creation")
    private Instant dateCreation;

    private boolean isActive = false;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;
}
