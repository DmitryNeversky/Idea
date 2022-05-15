package org.dneversky.gateway.api.browser;

import lombok.RequiredArgsConstructor;
import org.dneversky.gateway.security.UserPrincipal;
import org.dneversky.gateway.servie.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public String getUsername() {
        userServiceImpl.getUserByUsername("e@e");
        return "Ok";
    }
}
