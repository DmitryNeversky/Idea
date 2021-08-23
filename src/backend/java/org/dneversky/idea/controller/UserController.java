package org.dneversky.idea.controller;

import org.dneversky.idea.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("users")
public class UserController {

    @GetMapping("/auth")
    public void auth(@RequestBody User user) {

    }
}
