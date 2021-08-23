package org.dneversky.idea.controller;

import org.dneversky.idea.entity.User;
import org.dneversky.idea.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth")
    public ResponseEntity<Boolean> auth(@RequestBody User user) {
        User findUser = (User) userService.loadUserByUsername(user.getUsername());
        if(findUser == null)
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(true, HttpStatus.FOUND);
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody User user) {
        User findUser = (User) userService.loadUserByUsername(user.getUsername());

        if(findUser != null) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }

        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }
}
