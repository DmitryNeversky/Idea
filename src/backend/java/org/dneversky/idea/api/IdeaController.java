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

        return new ResponseEntity<>(ideaService.get(id), HttpStatus.OK);
    }

    @PostMapping("/idea/save")
    public ResponseEntity<Idea> saveIdea(@RequestPart("idea") @Valid Idea idea,
                                         @RequestPart("addImages") List<MultipartFile> addImages,
                                         @RequestPart("addFiles") List<MultipartFile> addFiles,
                                         Principal principal) {

        return new ResponseEntity<>(ideaService.add(idea, addImages, addFiles, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/idea/put")
    public ResponseEntity<Idea> putIdea(@RequestPart("idea") @Valid Idea idea,
                                        @RequestPart("addImages") List<MultipartFile> addImages,
                                        @RequestPart("addFiles") List<MultipartFile> addFiles) {

        return new ResponseEntity<>(ideaService.put(idea, addImages, addFiles), HttpStatus.OK);
    }

    @DeleteMapping("/idea/delete/{idea}")
    public ResponseEntity<Idea> deleteIdea(@PathVariable Idea idea) {
        ideaService.delete(idea);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
