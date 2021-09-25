package org.dneversky.idea.service;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.model.Status;
import org.dneversky.idea.payload.IdeaRequest;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface IdeaService {

    List<Idea> getAllIdeas();

    Idea getIdea(Long id);

    Idea saveIdea(IdeaRequest ideaRequest, List<MultipartFile> addImages, List<MultipartFile> addFiles, Principal principal);

    Idea updateIdea(Long id, IdeaRequest ideaRequest, List<MultipartFile> addImages, List<MultipartFile> addFiles);

    void deleteIdea(Long id);

    Idea addLook(Long id, Principal principal);

    Idea addRating(Long id, Principal principal);

    Idea reduceRating(Long id, Principal principal);

    Idea changeStatus(Long id, Status status);
}
