package org.dneversky.idea.api;

import org.dneversky.idea.entity.Tag;
import org.dneversky.idea.payload.TagRequest;
import org.dneversky.idea.service.impl.TagServiceImpl;
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

    public TagController(TagServiceImpl tagServiceImpl) {
        this.tagServiceImpl = tagServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {

        return ResponseEntity.ok(tagServiceImpl.getAllTags());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable String id) {

        return ResponseEntity.ok(tagServiceImpl.getTagById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Tag> getTag(@PathVariable String name) {

        return ResponseEntity.ok(tagServiceImpl.getTag(name));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PostMapping
    public ResponseEntity<Tag> saveTag(@RequestBody @Valid TagRequest tagRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(tagServiceImpl.saveTag(tagRequest));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable String id, @RequestBody @Valid TagRequest tagRequest) {

        return ResponseEntity.ok(tagServiceImpl.updateTag(id, tagRequest));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable String id) {
        tagServiceImpl.deleteTag(id);

        return ResponseEntity.noContent().build();
    }
}
