package com.logistics.service;

import com.logistics.model.Role;
import com.logistics.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
//
//    public Role updateRole(Long id, Role roleDetails) {
//        Role role = roleRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Role not found for id: " + id));
//
//        role.setName(roleDetails.getName());
//        role.setDescription(roleDetails.getDescription());
//
//        return roleRepository.save(role);
//    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found for id: " + id));
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
