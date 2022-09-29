package org.dneversky.idea.api;

import org.dneversky.idea.entity.Role;
import org.dneversky.idea.payload.RoleRequest;
import org.dneversky.idea.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/roles")
public class RoleController {

    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public RoleController(RoleServiceImpl roleServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok().body(roleServiceImpl.getAllRoles());
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PostMapping
    public ResponseEntity<Role> saveRole(@RequestBody @Valid RoleRequest roleRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleServiceImpl.saveRole(roleRequest));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<Role> saveRole(@PathVariable int id, @RequestBody @Valid RoleRequest roleRequest) {
        return ResponseEntity.ok(roleServiceImpl.updateRole(id, roleRequest));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable int id) {
        roleServiceImpl.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Role> getRole(@PathVariable int id) {
        return ResponseEntity.ok().body(roleServiceImpl.getRole(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Role> getRole(@PathVariable String name) {
        return ResponseEntity.ok().body(roleServiceImpl.getRole(name));
    }
}
