package org.dneversky.idea.api;

import lombok.RequiredArgsConstructor;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {

        return ResponseEntity.ok((userService.getUserById(id)));
    }

    @GetMapping("/user/current")
    public ResponseEntity<User> getCurrentUser(Principal principal) {

        return ResponseEntity.ok(userService.getUserByUsername(principal.getName()));
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
        if(userService.getUserByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.FOUND).build();
        }

        return ResponseEntity
                .created(URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString()))
                .body(userService.saveUser(user));
    }

    @PutMapping("/user/put")
    public ResponseEntity<User> putUser(@RequestPart("user") @Valid User user,
                                        @RequestPart(name = "avatar", required = false) MultipartFile avatar,
                                        @RequestPart(name = "removeAvatar", required = false) String removeAvatar) {

        return ResponseEntity.ok(userService.putUser(user, avatar, Boolean.parseBoolean(removeAvatar)));
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/notification/delete/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Integer id, Principal principal) {
        userService.deleteNotificationById(id, userService.getUserByUsername(principal.getName()));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/code")
    public ResponseEntity<?> getCode() {
        // send a code on email
        return ResponseEntity.ok().build();
    }

    @PostMapping("/code")
    public ResponseEntity<?> postCode(@RequestParam String key) {
        if(!key.equals("key"))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/password/change")
    public ResponseEntity<?> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, Principal principal) {
        if(!userService.verifyOldPassword(principal.getName(), oldPassword)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("старый пароль не совпадает с текущим");
        } else if(userService.verifyNewPassword(principal.getName(), newPassword)) {
            return ResponseEntity.status(HttpStatus.FOUND).body("новый пароль эквивалентен текущему");
        }
        userService.changePassword(principal.getName(), newPassword);

        return ResponseEntity.ok().build();
    }
}