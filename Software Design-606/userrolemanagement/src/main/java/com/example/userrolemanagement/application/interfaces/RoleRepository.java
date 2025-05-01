package com.example.userrolemanagement.application.interfaces;

import com.example.userrolemanagement.domain.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {
    Role save(Role role);
    Optional<Role> findById(UUID id);
    List<Role> findAll();
}
