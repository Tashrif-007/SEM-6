package com.example.userrolemanagement.application;

import com.example.userrolemanagement.application.interfaces.RoleRepository;
import com.example.userrolemanagement.domain.Role;

import java.util.List;
import java.util.UUID;

public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String roleName) {
        validateRoleInput(roleName);
        Role role = new Role(roleName);
        return roleRepository.save(role);
    }

    public Role getRoleById(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role with ID " + id + " not found"));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    private void validateRoleInput(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be empty");
        }
    }

    public static class RoleNotFoundException extends RuntimeException {
        public RoleNotFoundException(String message) {
            super(message);
        }
    }
}