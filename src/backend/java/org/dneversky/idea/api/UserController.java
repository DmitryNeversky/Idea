package org.dneversky.idea.api;

import lombok.RequiredArgsConstructor;
import org.dneversky.idea.entity.Role;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.entity.settings.NoticeSetting;
import org.dneversky.idea.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        return ResponseEntity.ok(userServiceImpl.getUsers());
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid User user) {
        if(userServiceImpl.getUserByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userServiceImpl.saveUser(user));
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestPart("user") @Valid User user,
                                       @RequestPart(name = "avatar", required = false) MultipartFile avatar,
                                       @RequestPart(name = "removeAvatar", required = false) String removeAvatar) {

        return ResponseEntity.ok(userServiceImpl.putUser(user, avatar, Boolean.parseBoolean(removeAvatar)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        userServiceImpl.deleteUser(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {

        return ResponseEntity.ok((userServiceImpl.getUserById(id)));
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Principal principal) {

        return ResponseEntity.ok(userServiceImpl.getUserByUsername(principal.getName()));
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, Principal principal) {
        if(!userServiceImpl.verifyOldPassword(principal.getName(), oldPassword)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("старый пароль не совпадает с текущим");
        } else if(userServiceImpl.verifyNewPassword(principal.getName(), newPassword)) {
            return ResponseEntity.status(HttpStatus.FOUND).body("новый пароль эквивалентен текущему");
        }
        userServiceImpl.changePassword(principal.getName(), newPassword);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/settings/notifies")
    public ResponseEntity<?> setNoticeSetting(@RequestBody NoticeSetting noticeSetting, Principal principal) {
        userServiceImpl.setNoticeSetting(principal.getName(), noticeSetting);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{username}/block")
    public ResponseEntity<?> blockUser(@PathVariable String username) {
        userServiceImpl.blockUser(username);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{username}/unblock")
    public ResponseEntity<?> unblockUser(@PathVariable String username) {
        userServiceImpl.unblockUser(username);

        return ResponseEntity.ok().build();
    }

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