package com.example.userrolemanagement.domain;

import java.util.UUID;

public class Role {
    private final UUID id;
    private String roleName;

    public Role(UUID id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Role(String roleName) {
        this.id = UUID.randomUUID();
        this.roleName = roleName;
    }

    public UUID getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
