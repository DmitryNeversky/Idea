package org.dneversky.idea.api;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.service.IdeaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Set;

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
    public ResponseEntity<Idea> saveIdea(@RequestParam String title, @RequestParam String text,
                                    @RequestParam(required = false) Set<String> tags,
                                    @RequestParam(required = false) List<MultipartFile> images,
                                    @RequestParam(required = false) List<MultipartFile> files,
                                    Principal principal) {

        return new ResponseEntity<>(ideaService.add(title, text, tags, images, files, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/idea/put/{idea}")
    public ResponseEntity<Idea> putIdea(@PathVariable Idea idea, @RequestParam String title,
                                         @RequestParam String text, @RequestParam(required = false) Set<String> tags,
                                         @RequestParam(required = false) List<MultipartFile> addImages,
                                         @RequestParam(required = false) List<String> removeImages,
                                         @RequestParam(required = false) List<MultipartFile> addFiles,
                                         @RequestParam(required = false) List<String> removeFiles) {

        return new ResponseEntity<>(ideaService.put(idea, title, text, tags, addImages, removeImages, addFiles, removeFiles), HttpStatus.OK);
    }

    @DeleteMapping("/idea/delete/{idea}")
    public ResponseEntity<Idea> deleteIdea(@PathVariable Idea idea) {
        ideaService.delete(idea);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
