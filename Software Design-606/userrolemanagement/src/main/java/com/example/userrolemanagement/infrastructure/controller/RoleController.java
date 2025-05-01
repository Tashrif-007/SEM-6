package com.example.userrolemanagement.infrastructure.controller;

import com.example.userrolemanagement.application.RoleService;
import com.example.userrolemanagement.domain.Role;
import com.example.userrolemanagement.infrastructure.controller.dto.CreateRoleRequest;
import com.example.userrolemanagement.infrastructure.controller.dto.RoleDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Map<String, UUID>> createRole(@Valid @RequestBody CreateRoleRequest request) {
        Role createdRole = roleService.createRole(request.getRoleName());
        Map<String, UUID> response = new HashMap<>();
        response.put("id", createdRole.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable UUID id) {
        Role role = roleService.getRoleById(id);
        return ResponseEntity.ok(RoleDTO.fromDomain(role));
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles().stream()
                .map(RoleDTO::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }
}