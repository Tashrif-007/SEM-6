package com.example.userrolemanagement.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User {
    private final UUID id;
    private String name;
    private String email;
    private final Set<Role> roles;

    public User(UUID id,String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.roles = new HashSet<>();
    }
    public User(String email, String name) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.name = name;
        this.roles = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    public void assignRole(Role role) {
        roles.add(role);
    }
    public void removeRole(Role role) {
        roles.remove(role);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
