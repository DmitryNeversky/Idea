package org.dneversky.idea.service;

import lombok.RequiredArgsConstructor;
import org.dneversky.idea.entity.Role;
import org.dneversky.idea.repository.RoleRepository;
import org.dneversky.idea.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public List<Role> getRoles() {

        return roleRepository.findAll();
    }

    public Role getRoleById(int id) {
        if(!roleRepository.findById(id).isPresent())
            return null;

        return roleRepository.findById(id).get();
    }

    public Role getRoleByName(String roleName) {

        return roleRepository.findByName(roleName);
    }

    public Role saveRole(Role role) {
        if(roleRepository.findByName(role.getName()) != null) {
            return null;
        }

        return roleRepository.save(role);
    }

    public void deleteRole(Role role) {
        userRepository.findAll().forEach(u -> {
            if(u.getRoles().contains(role)) {
                u.getRoles().remove(role);
                userRepository.save(u);
            }
        });

        roleRepository.delete(role);
    }

    @PostConstruct
    private void init() {
        if(roleRepository.findByName("USER") == null) {
            roleRepository.save(new Role("USER"));
        }
        if(roleRepository.findByName("ADMIN") == null) {
            roleRepository.save(new Role("ADMIN"));
        }
        if(roleRepository.findByName("SUPER_ADMIN") == null) {
            roleRepository.save(new Role("SUPER_ADMIN"));
        }
    }
}
