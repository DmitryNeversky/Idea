package org.dneversky.idea.api;

import org.dneversky.idea.entity.Role;
import org.dneversky.idea.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {

        return ResponseEntity.ok().body(roleService.getRoles());
    }

    @PostMapping
    public ResponseEntity<Role> save(@RequestBody @Valid Role role) {

        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.saveRole(role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Role id) {
        roleService.deleteRole(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable int id) {

        return ResponseEntity.ok().body(roleService.getRoleById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String name) {

        return ResponseEntity.ok().body(roleService.getRoleByName(name));
    }
}
