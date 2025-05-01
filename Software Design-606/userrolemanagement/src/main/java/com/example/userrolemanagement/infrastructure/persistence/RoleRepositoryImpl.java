package com.example.userrolemanagement.infrastructure.persistence;

import com.example.userrolemanagement.application.interfaces.RoleRepository;
import com.example.userrolemanagement.domain.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RoleRepositoryImpl implements RoleRepository {
    private final RoleJpaRepository roleJpaRepository;

    public RoleRepositoryImpl(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public Role save(Role role) {
        RoleJpaEntity jpaEntity = RoleJpaEntity.fromDomainEntity(role);
        RoleJpaEntity savedEntity = roleJpaRepository.save(jpaEntity);
        return savedEntity.toDomainEntity();
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return roleJpaRepository.findById(id.toString())
                .map(RoleJpaEntity::toDomainEntity);
    }

    @Override
    public List<Role> findAll() {
        return roleJpaRepository.findAll().stream()
                .map(RoleJpaEntity::toDomainEntity)
                .collect(Collectors.toList());
    }
}