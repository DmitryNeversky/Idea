package org.dneversky.idea.api.v1;

import org.dneversky.idea.advice.annotation.PrincipalOrAdminAccess;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.payload.PasswordChangeRequest;
import org.dneversky.idea.payload.UserRequest;
import org.dneversky.idea.security.CurrentUser;
import org.dneversky.idea.security.UserPrincipal;
import org.dneversky.idea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("userServiceFacade") UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return ResponseEntity.ok((userService.getUser(id)));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok((userService.getUser(username)));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestPart("user") @Valid User user, @RequestPart("admin") boolean admin) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user, admin));
    }

    @PutMapping("/{username}")
    @PrincipalOrAdminAccess
    public ResponseEntity<User> updateUser(@PathVariable String username,
                                           @RequestPart("user") @Valid UserRequest userRequest,
                                           @RequestPart(name = "avatar", required = false) MultipartFile avatar,
                                           @RequestPart(name = "removeAvatar", required = false) String removeAvatar) {
        return ResponseEntity.ok(userService.updateUser(username, userRequest, avatar, Boolean.parseBoolean(removeAvatar)));
    }

    @DeleteMapping("/{username}")
    @PrincipalOrAdminAccess
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(@CurrentUser UserPrincipal principal) {
        return ResponseEntity.ok(userService.getUser(principal.getUsername()));
    }

    @PatchMapping("/{username}/password")
    @PrincipalOrAdminAccess
    public ResponseEntity<?> changePassword(@PathVariable String username,
                                            @Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        if(!userService.verifyOldPassword(username, passwordChangeRequest.getCurrentPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("старый пароль не совпадает с текущим");
        } else if(userService.verifyNewPassword(username, passwordChangeRequest.getNewPassword())) {
            return ResponseEntity.status(HttpStatus.FOUND).body("новый пароль эквивалентен текущему");
        }
        userService.patchPassword(username, passwordChangeRequest);
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_ADMIN"})
    @PatchMapping("/{username}/block")
    public ResponseEntity<?> blockUserByUsername(@PathVariable String username) {
        userService.blockUser(username);
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_ADMIN"})
    @PatchMapping("/{username}/unblock")
    public ResponseEntity<?> unblockUserByUsername(@PathVariable String username) {
        userService.unblockUser(username);
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/{username}/role")
    public ResponseEntity<?> changeRole(@PathVariable String username, @RequestBody String role) {
        userService.changeRoles(username, role);
        return ResponseEntity.ok().build();
    }
}
