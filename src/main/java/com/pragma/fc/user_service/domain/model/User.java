package com.pragma.fc.user_service.domain.model;

import java.time.LocalDate;

public class User {
    private Long documentNumber;
    private String name;
    private String lastname;
    private String email;
    private LocalDate birthDate;
    private String phone;
    private Role role;
    private String password;

    public User() {
    }

    public User(Long documentNumber, String name, String lastname, String email, LocalDate birthDate, String phone, Role role, String password) {
        this.documentNumber = documentNumber;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.birthDate = birthDate;
        this.phone = phone;
        this.role = role;
        this.password = password;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
