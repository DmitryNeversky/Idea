package org.dneversky.idea.api;

import org.dneversky.idea.entity.Tag;
import org.dneversky.idea.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Tag>> getTags() {

        return ResponseEntity.ok(tagService.getTags());
    }

    @PostMapping
    public ResponseEntity<Tag> save(@RequestBody @Valid Tag tag) {

        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.saveTag(tag));
    }

    @PutMapping
    public ResponseEntity<Tag> update(@RequestBody @Valid Tag tag) {

        return ResponseEntity.ok(tagService.putTag(tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Tag id) {
        tagService.deleteTag(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable int id) {

        return ResponseEntity.ok(tagService.getTagById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable String name) {

        return ResponseEntity.ok(tagService.getTagByName(name));
    }
}
