package org.dneversky.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.dneversky.user.entity.Role;
import org.dneversky.user.model.RoleRequest;
import org.dneversky.user.repository.RoleRepository;
import org.dneversky.user.repository.UserRepository;
import org.dneversky.user.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<Role> getAllRoles() {

        return roleRepository.findAll();
    }

    @Override
    public Role getRole(Integer id) {

        return roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role with id " + id + " not found in the database."));
    }

    @Override
    public Role getRole(String name) {

        return roleRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("Role with name " + name + " not found in the database."));
    }

    @Override
    public Role saveRole(RoleRequest roleRequest) {
        if(roleRepository.existsByName(roleRequest.getName())) {
            throw new EntityExistsException("Role with name " + roleRequest.getName() + " already exists.");
        }

        Role role = new Role();
        role.setName(roleRequest.getName());

        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Integer id, RoleRequest roleRequest) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role with id " + id + " not found in the database."));

        role.setName(roleRequest.getName());

        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Integer id) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role with id " + id + " not found in the database."));

        if(role.getUsers() != null && role.getUsers().size() > 0) {
            role.getUsers().forEach(user -> {
                user.getRoles().remove(role);
                userRepository.save(user); // saveAll()
            });
        }

        roleRepository.delete(role);
    }

    @PostConstruct
    private void init() {
        if(!roleRepository.findByName("USER").isPresent()) {
            roleRepository.save(new Role("USER"));
        }
        if(!roleRepository.findByName("ADMIN").isPresent()) {
            roleRepository.save(new Role("ADMIN"));
        }
        if(!roleRepository.findByName("SUPER_ADMIN").isPresent()) {
            roleRepository.save(new Role("SUPER_ADMIN"));
        }
    }
}
