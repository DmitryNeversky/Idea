package org.dneversky.idea.api;

import org.dneversky.idea.entity.Post;
import org.dneversky.idea.service.impl.PostServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private final PostServiceImpl postServiceImpl;

    public PostController(PostServiceImpl postServiceImpl) {
        this.postServiceImpl = postServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {

        return ResponseEntity.ok(postServiceImpl.getPosts());
    }

    @PostMapping
    public ResponseEntity<Post> save(@RequestBody @Valid Post post) {

        return ResponseEntity.status(HttpStatus.CREATED).body(postServiceImpl.savePost(post));
    }

    @PutMapping
    public ResponseEntity<Post> update(@RequestBody @Valid Post post) {

        return ResponseEntity.ok(postServiceImpl.putPost(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Post id) {
        postServiceImpl.deletePost(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable int id) {

        return ResponseEntity.ok(postServiceImpl.getPostById(id));
    }
}
