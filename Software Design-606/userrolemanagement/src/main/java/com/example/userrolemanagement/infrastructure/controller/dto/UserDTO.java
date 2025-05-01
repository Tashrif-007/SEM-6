package com.example.userrolemanagement.infrastructure.controller.dto;

import com.example.userrolemanagement.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserDTO {
    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    private Set<RoleDTO> roles;

    public UserDTO() {}

    public UserDTO(UUID id, String name, String email, Set<RoleDTO> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public static UserDTO fromDomain(User user) {
        Set<RoleDTO> roleDTOs = user.getRoles().stream()
                .map(RoleDTO::fromDomain)
                .collect(Collectors.toSet());

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                roleDTOs
        );
    }
}
