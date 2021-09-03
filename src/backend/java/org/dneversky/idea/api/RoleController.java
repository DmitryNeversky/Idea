package org.dneversky.idea.api;


import org.dneversky.idea.entity.Role;
import org.dneversky.idea.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api")
public class RoleController {

    private final UserService userService;

    public RoleController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {

        return ResponseEntity.ok().body(userService.getRoles());
    }

    @GetMapping("/role/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String name) {

        return ResponseEntity.ok().body(userService.getRoleByName(name));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody @Valid Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/{id}/delete")
    public ResponseEntity<?> deleteRole(@PathVariable Role id) {
        userService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
