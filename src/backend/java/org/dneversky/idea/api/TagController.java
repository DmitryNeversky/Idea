package org.dneversky.idea.api;

import org.dneversky.idea.entity.Tag;
import org.dneversky.idea.service.impl.TagServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Tag>> getTags() {

        return ResponseEntity.ok(tagServiceImpl.getTags());
    }

    @PostMapping
    public ResponseEntity<Tag> save(@RequestBody @Valid Tag tag) {

        return ResponseEntity.status(HttpStatus.CREATED).body(tagServiceImpl.saveTag(tag));
    }

    @PutMapping
    public ResponseEntity<Tag> update(@RequestBody @Valid Tag tag) {

        return ResponseEntity.ok(tagServiceImpl.putTag(tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Tag id) {
        tagServiceImpl.deleteTag(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable int id) {

        return ResponseEntity.ok(tagServiceImpl.getTagById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable String name) {

        return ResponseEntity.ok(tagServiceImpl.getTagByName(name));
    }
}
