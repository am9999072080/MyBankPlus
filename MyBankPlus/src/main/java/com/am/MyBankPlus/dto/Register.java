package com.am.MyBankPlus.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Register {
    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private Role role;
}