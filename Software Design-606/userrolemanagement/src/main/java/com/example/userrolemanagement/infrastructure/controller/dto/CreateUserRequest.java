package com.example.userrolemanagement.infrastructure.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    public CreateUserRequest() {}

    public CreateUserRequest(String name, String email) {
        this.name=name;
        this.email=email;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
}
