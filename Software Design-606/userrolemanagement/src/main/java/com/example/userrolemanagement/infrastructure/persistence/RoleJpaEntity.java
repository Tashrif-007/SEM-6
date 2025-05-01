package com.example.userrolemanagement.infrastructure.persistence;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class RoleJpaEntity {
    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(nullable = false, unique = true)
    private String roleName;

    public RoleJpaEntity() {
    }

    public RoleJpaEntity(String id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }


    public com.example.userrolemanagement.domain.Role toDomainEntity() {
        return new com.example.userrolemanagement.domain.Role(
                UUID.fromString(this.id),
                this.roleName
        );
    }

    public static RoleJpaEntity fromDomainEntity(com.example.userrolemanagement.domain.Role role) {
        return new RoleJpaEntity(
                role.getId().toString(),
                role.getRoleName()
        );
    }
}