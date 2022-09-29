package org.dneversky.idea.service;

import org.dneversky.idea.entity.Role;
import org.dneversky.idea.payload.RoleRequest;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRole(int id);

    Role getRole(String name);

    Role createRole(RoleRequest roleRequest);

    Role updateRole(int id, RoleRequest roleRequest);

    void deleteRole(int id);
}
