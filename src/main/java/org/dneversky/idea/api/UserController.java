package org.dneversky.idea.api;

import lombok.RequiredArgsConstructor;
import org.dneversky.idea.entity.Role;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.payload.PasswordChangeRequest;
import org.dneversky.idea.payload.UserRequest;
import org.dneversky.idea.security.CurrentUser;
import org.dneversky.idea.security.UserPrincipal;
import org.dneversky.idea.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok(userServiceImpl.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid User user) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userServiceImpl.saveUser(user));
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> update(@PathVariable String username,
                                       @CurrentUser UserPrincipal userPrincipal,
                                       @RequestPart("user") @Valid UserRequest userRequest,
                                       @RequestPart(name = "avatar", required = false) MultipartFile avatar,
                                       @RequestPart(name = "removeAvatar", required = false) String removeAvatar) {

        return ResponseEntity.ok(userServiceImpl.updateUser(username, userPrincipal, userRequest, avatar, Boolean.parseBoolean(removeAvatar)));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> delete(@PathVariable String username, @CurrentUser UserPrincipal userPrincipal) {
        userServiceImpl.deleteUser(username, userPrincipal);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok((userServiceImpl.getUser(id)));
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(@CurrentUser UserPrincipal principal) {

        return ResponseEntity.ok(userServiceImpl.getUserByUsername(principal.getUsername()));
    }

    @PatchMapping("/{username}/password")
    public ResponseEntity<?> changePassword(@PathVariable String username,
                                            @CurrentUser UserPrincipal userPrincipal,
                                            @Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {

        if(!userServiceImpl.verifyOldPassword(username, passwordChangeRequest.getCurrentPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("старый пароль не совпадает с текущим");
        } else if(userServiceImpl.verifyNewPassword(username, passwordChangeRequest.getNewPassword())) {
            return ResponseEntity.status(HttpStatus.FOUND).body("новый пароль эквивалентен текущему");
        }

        userServiceImpl.patchPassword(username, userPrincipal, passwordChangeRequest);

        return ResponseEntity.ok().build();
    }

    @Secured({"ADMIN", "SUPER_ADMIN"})
    @PatchMapping("/{username}/block")
    public ResponseEntity<?> blockUser(@PathVariable String username) {
        userServiceImpl.blockUser(username);

        return ResponseEntity.ok().build();
    }

    @Secured({"ADMIN", "SUPER_ADMIN"})
    @PatchMapping("/{username}/unblock")
    public ResponseEntity<?> unblockUser(@PathVariable String username) {
        userServiceImpl.unblockUser(username);

        return ResponseEntity.ok().build();
    }

    @Secured("SUPER_ADMIN")
    @PatchMapping("/{username}/roles")
    public ResponseEntity<?> changeRoles(@PathVariable String username, @RequestBody Set<Role> roles) {
        userServiceImpl.changeRoles(username, roles);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/notification/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Integer id, Principal principal) {
        userServiceImpl.deleteNotificationById(id, userServiceImpl.getUserByUsername(principal.getName()));

        return ResponseEntity.ok().build();
    }
}

//    @GetMapping("/code")
//    public ResponseEntity<?> getCode() {
//        // send a code on email
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/code")
//    public ResponseEntity<?> postCode(@RequestParam String key) {
//        if(!key.equals("key"))
//            return ResponseEntity.notFound().build();
//        return ResponseEntity.ok().build();
//    }