package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int staffID;

    private String fullName;
    private String email;
    private String password;
    private Date dateOfBirth;
    private char gender;
    private String address;
    private String phoneNumber;
    private boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "roleID", insertable = false, updatable = false)
    private Role role;

    @Column(name = "role_id")
    private int roleID;
}
