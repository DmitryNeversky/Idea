package org.dneversky.idea.service;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.model.Status;
import org.dneversky.idea.payload.IdeaRequest;
import org.dneversky.idea.security.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IdeaService {

    List<Idea> getAllIdeas();

    Idea getIdea(Long id);

    Idea saveIdea(IdeaRequest ideaRequest, List<MultipartFile> addImages, List<MultipartFile> addFiles, UserPrincipal userPrincipal);

    Idea updateIdea(Long id, IdeaRequest ideaRequest, List<MultipartFile> addImages, List<MultipartFile> addFiles, UserPrincipal userPrincipal);

    void deleteIdea(Long id, UserPrincipal userPrincipal);

    Idea addLook(Long id, UserPrincipal userPrincipal);

    Idea addRating(Long id, UserPrincipal userPrincipal);

    Idea reduceRating(Long id, UserPrincipal userPrincipal);

    Idea changeStatus(Long id, Status status, UserPrincipal userPrincipal);
}
