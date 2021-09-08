package org.dneversky.idea.api;

import org.dneversky.idea.entity.Tag;
import org.dneversky.idea.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getTags() {

        return ResponseEntity.ok(tagService.getTags());
    }

    @PostMapping("/tag/save")
    public ResponseEntity<Tag> saveTag(@RequestBody Tag tag) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(tagService.saveTag(tag));
    }

    @DeleteMapping("/tag/delete/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Tag id) {
        tagService.deleteTag(id);

        return ResponseEntity.noContent().build();
    }
}
