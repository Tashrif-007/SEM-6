package com.example.userrolemanagement.application;

import com.example.userrolemanagement.application.interfaces.RoleRepository;
import com.example.userrolemanagement.domain.Role;
import com.example.userrolemanagement.application.RoleService.RoleNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RoleServiceTest {

    private RoleRepository roleRepository;
    private RoleService roleService;

    @Before
    public void setUp() {
        roleRepository = mock(RoleRepository.class);
        roleService = new RoleService(roleRepository);
    }

    @Test
    public void testCreateRole() {
        String roleName = "ADMIN";
        Role mockRole = new Role(UUID.randomUUID(), roleName);

        when(roleRepository.save(any(Role.class))).thenReturn(mockRole);

        Role createdRole = roleService.createRole(roleName);

        assertNotNull(createdRole);
        assertEquals(roleName, createdRole.getRoleName());

//        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
//        verify(roleRepository).save(captor.capture());
//
//        Role capturedRole = captor.getValue();
//        assertEquals(roleName, capturedRole.getRoleName());
//        assertNotNull(capturedRole.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRoleWithEmptyNameThrowsException() {
        roleService.createRole("   ");
    }

    @Test
    public void testGetRoleById_Success() {
        UUID id = UUID.randomUUID();
        Role mockRole = new Role(id, "USER");

        when(roleRepository.findById(id)).thenReturn(Optional.of(mockRole));

        Role foundRole = roleService.getRoleById(id);

        assertNotNull(foundRole);
        assertEquals("USER", foundRole.getRoleName());
        assertEquals(id, foundRole.getId());
    }

    @Test(expected = RoleNotFoundException.class)
    public void testGetRoleById_NotFound() {
        UUID id = UUID.randomUUID();
        when(roleRepository.findById(id)).thenReturn(Optional.empty());

        roleService.getRoleById(id);
    }

    @Test
    public void testGetAllRoles() {
        Role role1 = new Role(UUID.randomUUID(), "ADMIN");
        Role role2 = new Role(UUID.randomUUID(), "USER");

        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        List<Role> roles = roleService.getAllRoles();

        assertEquals(2, roles.size());
        assertEquals("ADMIN", roles.get(0).getRoleName());
        assertEquals("USER", roles.get(1).getRoleName());
    }
}
