package org.dneversky.idea.api;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.model.Status;
import org.dneversky.idea.service.IdeaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RequestMapping("api")
@RestController
public class IdeaController {

    private final IdeaService ideaService;

    public IdeaController(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    @GetMapping("/ideas")
    public ResponseEntity<List<Idea>> getIdeas() {

        return ResponseEntity.ok(ideaService.getIdeas());
    }

    @GetMapping("/idea/{id}")
    public ResponseEntity<Idea> getIdeaById(@PathVariable int id) {

        return ResponseEntity.ok(ideaService.getIdeaById(id));
    }

    @PostMapping("/idea/save")
    public ResponseEntity<Idea> saveIdea(@RequestPart("idea") @Valid Idea idea,
                                         @RequestPart(required = false) List<MultipartFile> addImages,
                                         @RequestPart(required = false) List<MultipartFile> addFiles,
                                         Principal principal) {

        return ResponseEntity
                .created(URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/idea/save").toUriString()))
                .body(ideaService.saveIdea(idea, addImages, addFiles, principal.getName()));
    }

    @PutMapping("/idea/put")
    public ResponseEntity<Idea> putIdea(@RequestPart("idea") @Valid Idea idea,
                                        @RequestPart(value = "addImages", required = false) List<MultipartFile> addImages,
                                        @RequestPart(value = "addFiles", required = false) List<MultipartFile> addFiles) {

        return ResponseEntity.ok(ideaService.putIdea(idea, addImages, addFiles));
    }

    @PutMapping("/idea/{idea}/status")
    public ResponseEntity<Idea> changeStatus(@PathVariable Idea idea, @RequestBody String status) {

        return ResponseEntity.ok(ideaService.changeStatus(idea, Status.valueOf(status)));
    }

    @DeleteMapping("/idea/delete/{id}")
    public ResponseEntity<?> deleteIdea(@PathVariable int id) {
        ideaService.deleteIdea(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/idea/look/{idea}")
    public ResponseEntity<?> addLook(@PathVariable Idea idea, Principal principal) {
        ideaService.addLook(idea, principal.getName());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/idea/rating/add/{idea}")
    public ResponseEntity<?> addRating(@PathVariable Idea idea, Principal principal) {
        ideaService.addRating(idea, principal.getName());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/idea/rating/reduce/{idea}")
    public ResponseEntity<?> reduceRating(@PathVariable Idea idea, Principal principal) {
        ideaService.reduceRating(idea, principal.getName());

        return ResponseEntity.ok().build();
    }
}
