package com.miw.feature.authentication.core.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "APP_USER")
public class User {

    // Attributes

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private Role role;

    // Empty constructor

    protected User() {
    }

    // All args constructor

    public User(int id, String username, String password, Role role) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setRole(role);
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    // Setters

    private void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must not be negative");
        this.id = id;
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("username must not be blank");
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("password must not be blank");
        this.password = password;
    }

    public void setRole(Role role) {
        if (role == null) throw new IllegalArgumentException("Role is required");
        this.role = role;
    }


    // Equals and hashCode


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
