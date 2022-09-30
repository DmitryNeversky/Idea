package org.dneversky.idea.api;

import org.dneversky.idea.entity.Role;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.payload.PasswordChangeRequest;
import org.dneversky.idea.payload.UserRequest;
import org.dneversky.idea.security.CurrentUser;
import org.dneversky.idea.security.UserPrincipal;
import org.dneversky.idea.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userServiceImpl.getAllUsers());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return ResponseEntity.ok((userServiceImpl.getUser(id)));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok((userServiceImpl.getUser(username)));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestPart("user") @Valid User user, @RequestPart("admin") boolean admin) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userServiceImpl.createUser(user, admin));
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @CurrentUser UserPrincipal userPrincipal,
                                           @RequestPart("user") @Valid UserRequest userRequest,
                                           @RequestPart(name = "avatar", required = false) MultipartFile avatar,
                                           @RequestPart(name = "removeAvatar", required = false) String removeAvatar) {
        return ResponseEntity.ok(userServiceImpl.updateUser(username, userPrincipal, userRequest, avatar, Boolean.parseBoolean(removeAvatar)));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username, @CurrentUser UserPrincipal userPrincipal) {
        userServiceImpl.deleteUser(username, userPrincipal);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(@CurrentUser UserPrincipal principal) {
        return ResponseEntity.ok(userServiceImpl.getUser(principal.getUsername()));
    }

    @PatchMapping("/{username}/password")
    public ResponseEntity<?> changePassword(@PathVariable String username, @CurrentUser UserPrincipal userPrincipal,
                                            @Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        if(!userServiceImpl.verifyOldPassword(username, passwordChangeRequest.getCurrentPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("старый пароль не совпадает с текущим");
        } else if(userServiceImpl.verifyNewPassword(username, passwordChangeRequest.getNewPassword())) {
            return ResponseEntity.status(HttpStatus.FOUND).body("новый пароль эквивалентен текущему");
        }
        userServiceImpl.patchPassword(username, userPrincipal, passwordChangeRequest);
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PatchMapping("/{username}/block")
    public ResponseEntity<?> blockUserByUsername(@PathVariable String username, @CurrentUser UserPrincipal userPrincipal) {
        userServiceImpl.blockUser(username, userPrincipal);
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PatchMapping("/{username}/unblock")
    public ResponseEntity<?> unblockUserByUsername(@PathVariable String username, @CurrentUser UserPrincipal userPrincipal) {
        userServiceImpl.unblockUser(username, userPrincipal);
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_SUPER_ADMIN")
    @PatchMapping("/{username}/roles")
    public ResponseEntity<?> changeRoles(@PathVariable String username, @RequestBody Set<Role> roles, @CurrentUser UserPrincipal userPrincipal) {
        userServiceImpl.changeRoles(username, roles, userPrincipal);
        return ResponseEntity.ok().build();
    }
}
