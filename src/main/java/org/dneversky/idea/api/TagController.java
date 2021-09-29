package org.dneversky.idea.api;

import org.dneversky.idea.entity.Tag;
import org.dneversky.idea.payload.TagRequest;
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
    public ResponseEntity<List<Tag>> getAllTags() {

        return ResponseEntity.ok(tagServiceImpl.getAllTags());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable Integer id) {

        return ResponseEntity.ok(tagServiceImpl.getTag(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Tag> getTag(@PathVariable String name) {

        return ResponseEntity.ok(tagServiceImpl.getTag(name));
    }

    @PostMapping
    public ResponseEntity<Tag> saveTag(@RequestBody @Valid TagRequest tagRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(tagServiceImpl.saveTag(tagRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Integer id, @RequestBody @Valid TagRequest tagRequest) {

        return ResponseEntity.ok(tagServiceImpl.updateTag(id, tagRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Integer id) {
        tagServiceImpl.deleteTag(id);

        return ResponseEntity.noContent().build();
    }
}
