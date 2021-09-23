package org.dneversky.idea.api;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.model.Status;
import org.dneversky.idea.service.IdeaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("api/ideas")
@RestController
public class IdeaController {

    private final IdeaService ideaService;

    public IdeaController(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    @GetMapping
    public ResponseEntity<List<Idea>> getIdeas() {

        return ResponseEntity.ok(ideaService.getIdeas());
    }

    @PostMapping
    public ResponseEntity<Idea> save(@RequestPart("idea") @Valid Idea idea,
                                         @RequestPart(required = false) List<MultipartFile> addImages,
                                         @RequestPart(required = false) List<MultipartFile> addFiles,
                                         Principal principal) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ideaService.saveIdea(idea, addImages, addFiles, principal.getName()));
    }

    @PutMapping
    public ResponseEntity<Idea> update(@RequestPart("idea") @Valid Idea idea,
                                        @RequestPart(value = "addImages", required = false) List<MultipartFile> addImages,
                                        @RequestPart(value = "addFiles", required = false) List<MultipartFile> addFiles) {

        return ResponseEntity.ok(ideaService.putIdea(idea, addImages, addFiles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        ideaService.deleteIdea(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idea> getById(@PathVariable int id) {

        return ResponseEntity.ok(ideaService.getIdeaById(id));
    }

    @PatchMapping("/{idea}/status")
    public ResponseEntity<Idea> changeStatus(@PathVariable Idea idea, @RequestBody String status) {

        return ResponseEntity.ok(ideaService.changeStatus(idea, Status.valueOf(status)));
    }

    @PatchMapping("/{idea}/look")
    public ResponseEntity<?> addLook(@PathVariable Idea idea, Principal principal) {
        ideaService.addLook(idea, principal.getName());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{idea}/rating/add")
    public ResponseEntity<?> addRating(@PathVariable Idea idea, Principal principal) {
        ideaService.addRating(idea, principal.getName());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{idea}/rating/reduce")
    public ResponseEntity<?> reduceRating(@PathVariable Idea idea, Principal principal) {
        ideaService.reduceRating(idea, principal.getName());

        return ResponseEntity.ok().build();
    }
}
