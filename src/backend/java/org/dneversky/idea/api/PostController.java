package org.dneversky.idea.api;

import org.dneversky.idea.entity.Post;
import org.dneversky.idea.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {

        return ResponseEntity.ok(postService.getPosts());
    }

    @PostMapping
    public ResponseEntity<Post> save(@RequestBody @Valid Post post) {

        return ResponseEntity.status(HttpStatus.CREATED).body(postService.savePost(post));
    }

    @PutMapping
    public ResponseEntity<Post> update(@RequestBody @Valid Post post) {

        return ResponseEntity.ok(postService.putPost(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Post id) {
        postService.deletePost(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable int id) {

        return ResponseEntity.ok(postService.getPostById(id));
    }
}
