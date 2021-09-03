package org.dneversky.idea.api;

import org.dneversky.idea.entity.Post;
import org.dneversky.idea.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api")
public class PostController {

    private final UserService userService;

    public PostController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {

        return ResponseEntity.ok().body(userService.getPosts());
    }

    @PostMapping("/post/save")
    public ResponseEntity<Post> savePost(@RequestBody @Valid Post post) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/post/save").toUriString());
        return ResponseEntity.created(uri).body(userService.savePost(post));
    }

    @PostMapping("/post/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Post id) {
        userService.deletePost(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
