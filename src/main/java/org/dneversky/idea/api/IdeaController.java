package org.dneversky.idea.api;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.model.Status;
import org.dneversky.idea.payload.IdeaRequest;
import org.dneversky.idea.security.CurrentUser;
import org.dneversky.idea.security.UserPrincipal;
import org.dneversky.idea.service.impl.IdeaServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

    @GetMapping("/pag")
    public ResponseEntity<Page<Idea>> getPageIdeas(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false, defaultValue = "createdDate") String sortBy) {

        return ResponseEntity.ok(ideaServiceImpl.getIdeas(page, size, sortDirection, sortBy));
    }

    @PostMapping
    public ResponseEntity<Idea> save(@RequestPart("idea") @Valid IdeaRequest idea,
                                     @RequestPart(required = false) List<MultipartFile> addImages,
                                     @RequestPart(required = false) List<MultipartFile> addFiles,
                                     @CurrentUser UserPrincipal userPrincipal) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ideaServiceImpl.saveIdea(idea, addImages, addFiles, userPrincipal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Idea> update(@PathVariable Long id,
                                        @RequestPart("idea") @Valid IdeaRequest idea,
                                        @RequestPart(value = "addImages", required = false) List<MultipartFile> addImages,
                                        @RequestPart(value = "addFiles", required = false) List<MultipartFile> addFiles,
                                        @CurrentUser UserPrincipal userPrincipal) {

        return ResponseEntity.ok(ideaServiceImpl.updateIdea(id, idea, addImages, addFiles, userPrincipal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
        ideaServiceImpl.deleteIdea(id, userPrincipal);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idea> getById(@PathVariable Long id) {

        return ResponseEntity.ok(ideaServiceImpl.getIdea(id));
    }

    @Secured({"ADMIN", "SUPER_ADMIN"})
    @PatchMapping("/{id}/status")
    public ResponseEntity<Idea> changeStatus(@PathVariable Long id, @RequestBody String status, @CurrentUser UserPrincipal userPrincipal) {

        return ResponseEntity.ok(ideaServiceImpl.changeStatus(id, Status.valueOf(status), userPrincipal));
    }

    @PatchMapping("/{id}/look")
    public ResponseEntity<Idea> addLook(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {

        return ResponseEntity.ok(ideaServiceImpl.addLook(id, userPrincipal));
    }

    @PatchMapping("/{id}/rating/add")
    public ResponseEntity<Idea> addRating(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {

        return ResponseEntity.ok(ideaServiceImpl.addRating(id, userPrincipal));
    }

    @PatchMapping("/{id}/rating/reduce")
    public ResponseEntity<Idea> reduceRating(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {

        return ResponseEntity.ok(ideaServiceImpl.reduceRating(id, userPrincipal));
    }
}
