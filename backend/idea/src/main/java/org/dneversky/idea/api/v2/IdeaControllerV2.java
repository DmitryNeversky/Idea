package org.dneversky.idea.api.v2;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.model.Status;
import org.dneversky.idea.payload.IdeaRequest;
import org.dneversky.idea.security.CurrentUser;
import org.dneversky.idea.security.UserPrincipal;
import org.dneversky.idea.service.impl.IdeaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v2/ideas")
public class IdeaControllerV2 {

    private final IdeaServiceImpl ideaServiceImpl;

    @Autowired
    public IdeaControllerV2(IdeaServiceImpl ideaServiceImpl) {
        this.ideaServiceImpl = ideaServiceImpl;
    }

    @GetMapping("/amount")
    public Long getIdeaAmount() {
        return ideaServiceImpl.getIdeaAmount();
    }

    @GetMapping
    public ResponseEntity<Page<Idea>> getAllIdeas(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "5") Integer size,
                                                  @RequestParam(required = false, defaultValue = "ASC") String sortDirection,
                                                  @RequestParam(required = false, defaultValue = "createdDate") String sortBy) {
        return ResponseEntity.ok(ideaServiceImpl.getAllIdeas(page, size, sortDirection, sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idea> getIdea(@PathVariable long id) {
        return ResponseEntity.ok(ideaServiceImpl.getIdea(id));
    }

    @PostMapping
    public ResponseEntity<Idea> createIdea(@RequestPart("idea") @Valid Idea ideaRequest,
                                           @RequestPart(required = false) List<MultipartFile> attachedImages,
                                           @RequestPart(required = false) List<MultipartFile> attachedFiles,
                                           @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ideaServiceImpl.createIdea(ideaRequest, attachedImages, attachedFiles, userPrincipal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Idea> updateIdea(@PathVariable long id, @RequestPart("idea") @Valid IdeaRequest idea,
                                           @RequestPart(value = "addImages", required = false) List<MultipartFile> addImages,
                                           @RequestPart(value = "addFiles", required = false) List<MultipartFile> addFiles,
                                           @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(ideaServiceImpl.updateIdea(id, idea, addImages, addFiles, userPrincipal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIdea(@PathVariable long id, @CurrentUser UserPrincipal userPrincipal) {
        ideaServiceImpl.deleteIdea(id, userPrincipal);
        return ResponseEntity.noContent().build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PatchMapping("/{id}/status")
    public ResponseEntity<Idea> changeStatus(@PathVariable long id, @RequestBody String status, @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(ideaServiceImpl.changeStatus(id, Status.valueOf(status), userPrincipal));
    }

    @PatchMapping("/{id}/look")
    public ResponseEntity<Idea> addLook(@PathVariable long id, @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(ideaServiceImpl.addLook(id, userPrincipal));
    }

    @PatchMapping("/{id}/rating/add")
    public ResponseEntity<Idea> addRating(@PathVariable long id, @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(ideaServiceImpl.addRating(id, userPrincipal));
    }

    @PatchMapping("/{id}/rating/reduce")
    public ResponseEntity<Idea> reduceRating(@PathVariable long id, @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(ideaServiceImpl.reduceRating(id, userPrincipal));
    }
}
