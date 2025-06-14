package com.am.MyBankPlus.model;

import com.am.MyBankPlus.dto.Role;
import com.am.MyBankPlus.dto.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    @Column(unique = true)
    private String phoneNumber;
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String email;
    private String password;
    @Transient
    private int age;
    private Status status = Status.GOOD_CLIENT;
    @OneToOne(cascade = CascadeType.ALL)
    private Card card;

    private Role role;

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
