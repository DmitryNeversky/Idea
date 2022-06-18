package org.dneversky.gateway.client;

import org.dneversky.gateway.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service", url = "localhost:${userService.port}")
public interface FeignUserClient {

    @GetMapping("/api/users")
    List<User> getUsers();

    @GetMapping("/api/users/id/{id}")
    User getUserById(@PathVariable("id") long id);

    @GetMapping("/api/users/username/{username}")
    User getUserByUsername(@PathVariable("username") String username);

    @PostMapping("/api/users")
    User addUser(@RequestBody User user);
}
