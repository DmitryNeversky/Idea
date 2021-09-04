package org.dneversky.idea.api;

import org.dneversky.idea.entity.Tag;
import org.dneversky.idea.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/tag/delete/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Tag id) {
        tagService.deleteTag(id);

        return ResponseEntity.noContent().build();
    }
}
