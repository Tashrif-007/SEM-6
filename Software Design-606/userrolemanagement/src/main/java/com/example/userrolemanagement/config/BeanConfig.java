package com.example.userrolemanagement.config;

import com.example.userrolemanagement.application.RoleService;
import com.example.userrolemanagement.application.UserService;
import com.example.userrolemanagement.application.interfaces.RoleRepository;
import com.example.userrolemanagement.application.interfaces.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public UserService userService(UserRepository userRepository, RoleRepository roleRepository) {
        return new UserService(userRepository, roleRepository);
    }

    @Bean
    public RoleService roleService(RoleRepository roleRepository) {
        return new RoleService(roleRepository);
    }
}
