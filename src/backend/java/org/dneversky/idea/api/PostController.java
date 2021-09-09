package org.dneversky.idea.api;

import org.dneversky.idea.entity.Post;
import org.dneversky.idea.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {

        return ResponseEntity.ok(postService.getPosts());
    }

    @PostMapping("/post/save")
    public ResponseEntity<Post> savePost(@RequestBody @Valid Post post) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/post/save").toUriString());
        return ResponseEntity.created(uri).body(postService.savePost(post));
    }

    @PutMapping("/post/put")
    public ResponseEntity<Post> putPost(@RequestBody @Valid Post post) {

        return ResponseEntity.ok(postService.putPost(post));
    }

    @DeleteMapping("/post/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Post id) {
        postService.deletePost(id);

        return ResponseEntity.noContent().build();
    }
}
