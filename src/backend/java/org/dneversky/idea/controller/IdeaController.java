package org.dneversky.idea.controller;

import org.dneversky.idea.model.Idea;
import org.dneversky.idea.service.IdeaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("ideas")
@RestController
public class IdeaController {

    private final IdeaService ideaService;

    public IdeaController(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    @GetMapping
    public ResponseEntity<List<Idea>> getAll() {

        return new ResponseEntity<>(ideaService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idea> get(@PathVariable String id) {

        return new ResponseEntity<>(ideaService.get(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Idea> add(@RequestParam String title, @RequestParam String text, @RequestParam List<String> tags) {

        return new ResponseEntity<>(ideaService.add(title, text, tags), HttpStatus.CREATED);
    }

    @PutMapping("/put/{idea}")
    public ResponseEntity<Idea> put(@PathVariable Idea idea) {

        return new ResponseEntity<>(ideaService.put(idea), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idea}")
    public ResponseEntity<Idea> delete(@PathVariable Idea idea) {
        ideaService.delete(idea);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
