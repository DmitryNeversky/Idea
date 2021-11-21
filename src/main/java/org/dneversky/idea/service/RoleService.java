package org.dneversky.idea.service;

import org.dneversky.idea.entity.Role;
import org.dneversky.idea.payload.RoleRequest;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRoleById(String id);

    Role getRole(String name);

    Role saveRole(RoleRequest roleRequest);

    Role updateRole(String id, RoleRequest roleRequest);

    void deleteRole(String id);
}
