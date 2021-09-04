package org.dneversky.idea.api;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.service.IdeaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

        return new ResponseEntity<>(ideaService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/idea/{id}")
    public ResponseEntity<Idea> getIdeaById(@PathVariable int id) {

        return new ResponseEntity<>(ideaService.getIdeaById(id), HttpStatus.OK);
    }

    @PostMapping("/idea/save")
    public ResponseEntity<Idea> saveIdea(@RequestPart("idea") @Valid Idea idea,
                                         @RequestPart(required = false) List<MultipartFile> addImages,
                                         @RequestPart(required = false) List<MultipartFile> addFiles,
                                         Principal principal) {

        return new ResponseEntity<>(ideaService.saveIdea(idea, addImages, addFiles, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/idea/put")
    public ResponseEntity<Idea> putIdea(@RequestPart("idea") @Valid Idea idea,
                                        @RequestPart(value = "addImages", required = false) List<MultipartFile> addImages,
                                        @RequestPart(value = "addFiles", required = false) List<MultipartFile> addFiles) {

        return new ResponseEntity<>(ideaService.putIdea(idea, addImages, addFiles), HttpStatus.OK);
    }

    @DeleteMapping("/idea/delete/{idea}")
    public ResponseEntity<?> deleteIdea(@PathVariable Idea idea) {
        ideaService.delete(idea);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/idea/look/{idea}")
    public ResponseEntity<?> addLook(@PathVariable Idea idea, Principal principal) {
        ideaService.addLook(idea, principal.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/idea/rating/add/{idea}")
    public ResponseEntity<?> addRating(@PathVariable Idea idea, Principal principal) {
        ideaService.addRating(idea, principal.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/idea/rating/reduce/{idea}")
    public ResponseEntity<?> reduceRating(@PathVariable Idea idea, Principal principal) {
        ideaService.reduceRating(idea, principal.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
