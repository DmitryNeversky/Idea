package org.dneversky.gateway.api.v1.browser;

import org.dneversky.gateway.dto.SaveUserRequest;
import org.dneversky.gateway.dto.UpdateUserRequest;
import org.dneversky.gateway.dto.UserResponse;
import org.dneversky.gateway.service.impl.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final DefaultUserService userService;

    @Autowired
    public UserController(DefaultUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {

        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/current")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {

        return ResponseEntity.ok(userService.getUserByUsername(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody SaveUserRequest userRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userRequest));
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserResponse> update(@PathVariable String username,
                                       @RequestPart("user") UpdateUserRequest userRequest,
                                       @RequestPart(name = "avatar", required = false) MultipartFile avatar,
                                       @RequestPart(name = "removeAvatar", required = false) String removeAvatar) {

        return ResponseEntity.ok(userService.updateUser(username, userRequest, avatar, Boolean.parseBoolean(removeAvatar)));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> delete(@PathVariable String username) {
        userService.deleteUser(username);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
//
//
//
//
//    @PatchMapping("/{username}/password")
//    public ResponseEntity<?> changePassword(@PathVariable String username,
//                                            @CurrentUser UserPrincipal userPrincipal,
//                                            @Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
//
//        if(!userServiceImpl.verifyOldPassword(username, passwordChangeRequest.getCurrentPassword())) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("старый пароль не совпадает с текущим");
//        } else if(userServiceImpl.verifyNewPassword(username, passwordChangeRequest.getNewPassword())) {
//            return ResponseEntity.status(HttpStatus.FOUND).body("новый пароль эквивалентен текущему");
//        }
//
//        userServiceImpl.patchPassword(username, userPrincipal, passwordChangeRequest);
//
//        return ResponseEntity.ok().build();
//    }
//
//    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
//    @PatchMapping("/{username}/block")
//    public ResponseEntity<?> blockUser(@PathVariable String username, @CurrentUser UserPrincipal userPrincipal) {
//        userServiceImpl.blockUser(username, userPrincipal);
//
//        return ResponseEntity.ok().build();
//    }
//
//    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
//    @PatchMapping("/{username}/unblock")
//    public ResponseEntity<?> unblockUser(@PathVariable String username, @CurrentUser UserPrincipal userPrincipal) {
//        userServiceImpl.unblockUser(username, userPrincipal);
//
//        return ResponseEntity.ok().build();
//    }
//
//    @Secured("ROLE_SUPER_ADMIN")
//    @PatchMapping("/{username}/roles")
//    public ResponseEntity<?> changeRoles(@PathVariable String username,
//                                         @RequestBody Set<Role> roles,
//                                         @CurrentUser UserPrincipal userPrincipal) {
//        userServiceImpl.changeRoles(username, roles, userPrincipal);
//
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/notification/{id}")
//    public ResponseEntity<?> deleteNotification(@PathVariable Integer id, @CurrentUser UserPrincipal userPrincipal) {
//        userServiceImpl.deleteNotificationById(id, userPrincipal);
//
//        return ResponseEntity.ok().build();
//    }
}
