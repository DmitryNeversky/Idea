package org.dneversky.idea.api;

import org.dneversky.idea.entity.Post;
import org.dneversky.idea.payload.PostRequest;
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
    public ResponseEntity<List<Post>> getAllPosts() {

        return ResponseEntity.ok(postServiceImpl.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody @Valid PostRequest postRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(postServiceImpl.savePost(postRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Integer id, @RequestBody @Valid PostRequest postRequest) {

        return ResponseEntity.ok(postServiceImpl.updatePost(id, postRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Integer id) {
        postServiceImpl.deletePost(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Integer id) {

        return ResponseEntity.ok(postServiceImpl.getPost(id));
    }
}
