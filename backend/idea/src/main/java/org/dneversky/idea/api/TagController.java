package org.dneversky.idea.api;

import org.dneversky.idea.entity.Tag;
import org.dneversky.idea.payload.TagRequest;
import org.dneversky.idea.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/tags")
public class TagController {

    private final TagServiceImpl tagServiceImpl;

    @Autowired
    public TagController(TagServiceImpl tagServiceImpl) {
        this.tagServiceImpl = tagServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagServiceImpl.getAllTags());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable int id) {
        return ResponseEntity.ok(tagServiceImpl.getTag(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable String name) {
        return ResponseEntity.ok(tagServiceImpl.getTag(name));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody @Valid TagRequest tagRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagServiceImpl.saveTag(tagRequest));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable int id, @RequestBody @Valid TagRequest tagRequest) {
        return ResponseEntity.ok(tagServiceImpl.updateTag(id, tagRequest));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable int id) {
        tagServiceImpl.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
