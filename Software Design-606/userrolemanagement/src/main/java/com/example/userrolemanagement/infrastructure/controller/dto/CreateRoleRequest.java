package com.example.userrolemanagement.infrastructure.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateRoleRequest {
    @NotBlank(message = "Role name cannot be blank")
    private String roleName;

    public CreateRoleRequest() {
    }

    public CreateRoleRequest(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}