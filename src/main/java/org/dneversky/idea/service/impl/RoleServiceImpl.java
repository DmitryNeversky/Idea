package org.dneversky.idea.service.impl;

import lombok.RequiredArgsConstructor;
import org.dneversky.idea.entity.Role;
import org.dneversky.idea.exception.EntityExistsException;
import org.dneversky.idea.exception.EntityNotFoundException;
import org.dneversky.idea.payload.RoleRequest;
import org.dneversky.idea.repository.RoleRepository;
import org.dneversky.idea.repository.UserRepository;
import org.dneversky.idea.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

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
    public Role getRoleById(String id) {

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
    public Role updateRole(String id, RoleRequest roleRequest) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role with id " + id + " not found in the database."));

        role.setName(roleRequest.getName());

        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(String id) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role with id " + id + " not found in the database."));

        // TODO: using index of user.roles to find and delete roles from users quickly
        userRepository.saveAll(
                userRepository.findAll()
                        .stream()
                        .sorted()
                        .filter(e -> e.getRoles().contains(role))
                        .peek(e -> e.getRoles().remove(role))
                        .collect(Collectors.toList()));

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
