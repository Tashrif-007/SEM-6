package com.example.userrolemanagement.infrastructure.controller.dto;

import com.example.userrolemanagement.domain.Role;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class RoleDTO {
    private UUID id;

    @NotBlank(message = "Role name cannot be blank")
    private String roleName;


    public RoleDTO(UUID id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public static RoleDTO fromDomain(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getRoleName()
        );
    }

    public Role toDomain() {
        return new Role(id, roleName);
    }
}