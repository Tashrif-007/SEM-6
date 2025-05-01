package com.example.userrolemanagement.infrastructure.persistence;

import com.example.userrolemanagement.application.interfaces.UserRepository;
import com.example.userrolemanagement.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity jpaEntity = UserJpaEntity.fromDomainEntity(user);
        UserJpaEntity savedEntity = userJpaRepository.save(jpaEntity);
        return savedEntity.toDomainEntity();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id.toString()).map(UserJpaEntity::toDomainEntity);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll().stream().map(UserJpaEntity::toDomainEntity).collect(Collectors.toList());
    }
}
