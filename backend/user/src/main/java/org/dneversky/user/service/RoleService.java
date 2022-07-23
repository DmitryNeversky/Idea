package org.dneversky.user.service;

import org.dneversky.user.entity.Role;
import org.dneversky.user.dto.RoleRequest;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRole(Integer id);

    Role getRole(String name);

    Role saveRole(RoleRequest roleRequest);

    Role updateRole(Integer id, RoleRequest roleRequest);

    void deleteRole(Integer id);
}
