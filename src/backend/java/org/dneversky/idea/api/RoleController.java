package org.dneversky.idea.api;

import org.dneversky.idea.entity.Role;
import org.dneversky.idea.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {

        return ResponseEntity.ok().body(roleService.getRoles());
    }

    @GetMapping("/role/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String name) {

        return ResponseEntity.ok().body(roleService.getRoleByName(name));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody @Valid Role role) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(roleService.saveRole(role));
    }

    @DeleteMapping("/role/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Role id) {
        roleService.deleteRole(id);

        return ResponseEntity.noContent().build();
    }
}
