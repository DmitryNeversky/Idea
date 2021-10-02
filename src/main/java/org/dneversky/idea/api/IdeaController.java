package org.dneversky.idea.api;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.model.Status;
import org.dneversky.idea.payload.IdeaRequest;
import org.dneversky.idea.service.impl.IdeaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("api/ideas")
@RestController
public class IdeaController {

    private final IdeaServiceImpl ideaServiceImpl;

    public IdeaController(IdeaServiceImpl ideaServiceImpl) {
        this.ideaServiceImpl = ideaServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Idea>> getIdeas() {

        return ResponseEntity.ok(ideaServiceImpl.getAllIdeas());
    }

    @PostMapping
    public ResponseEntity<Idea> save(@RequestPart("idea") @Valid IdeaRequest idea,
                                         @RequestPart(required = false) List<MultipartFile> addImages,
                                         @RequestPart(required = false) List<MultipartFile> addFiles,
                                         Principal principal) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ideaServiceImpl.saveIdea(idea, addImages, addFiles, principal.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Idea> update(@PathVariable Long id,
                                        @RequestPart("idea") @Valid IdeaRequest idea,
                                        @RequestPart(value = "addImages", required = false) List<MultipartFile> addImages,
                                        @RequestPart(value = "addFiles", required = false) List<MultipartFile> addFiles) {

        return ResponseEntity.ok(ideaServiceImpl.updateIdea(id, idea, addImages, addFiles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ideaServiceImpl.deleteIdea(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idea> getById(@PathVariable Long id) {

        return ResponseEntity.ok(ideaServiceImpl.getIdea(id));
    }

    @Secured({"ADMIN", "SUPER_ADMIN"})
    @PatchMapping("/{id}/status")
    public ResponseEntity<Idea> changeStatus(@PathVariable Long id, @RequestBody String status) {

        return ResponseEntity.ok(ideaServiceImpl.changeStatus(id, Status.valueOf(status)));
    }

    @PatchMapping("/{id}/look")
    public ResponseEntity<Idea> addLook(@PathVariable Long id, Principal principal) {

        return ResponseEntity.ok(ideaServiceImpl.addLook(id, principal.getName()));
    }

    @PatchMapping("/{id}/rating/add")
    public ResponseEntity<Idea> addRating(@PathVariable Long id, Principal principal) {

        return ResponseEntity.ok(ideaServiceImpl.addRating(id, principal.getName()));
    }

    @PatchMapping("/{id}/rating/reduce")
    public ResponseEntity<Idea> reduceRating(@PathVariable Long id, Principal principal) {

        return ResponseEntity.ok(ideaServiceImpl.reduceRating(id, principal.getName()));
    }
}
