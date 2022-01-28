package org.dneversky.idea.api;

import org.dneversky.idea.entity.Tag;
import org.dneversky.idea.payload.TagRequest;
import org.dneversky.idea.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {

        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable String id) {

        return ResponseEntity.ok(tagService.getTagById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Tag> getTag(@PathVariable String name) {

        return ResponseEntity.ok(tagService.getTag(name));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PostMapping
    public ResponseEntity<Tag> saveTag(@RequestBody @Valid TagRequest tagRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.saveTag(tagRequest));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable String id, @RequestBody @Valid TagRequest tagRequest) {

        return ResponseEntity.ok(tagService.updateTag(id, tagRequest));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable String id) {
        tagService.deleteTag(id);

        return ResponseEntity.noContent().build();
    }
}
