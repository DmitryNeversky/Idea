package org.dneversky.idea.service;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.model.Status;
import org.dneversky.idea.payload.IdeaRequest;
import org.dneversky.idea.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IdeaService {

    Long getIdeaAmount();

    Page<Idea> getAllIdeas(int from, int to, String direction, String sortBy);

    List<Idea> getAllIdeas();

    Idea getIdea(long id);

    Idea createIdea(Idea idea, List<MultipartFile> attachedImages, List<MultipartFile> attachedFiles, UserPrincipal userPrincipal);

    Idea updateIdea(long id, IdeaRequest ideaRequest, List<MultipartFile> attachedImages, List<MultipartFile> attachedFiles, UserPrincipal userPrincipal);

    void deleteIdea(long id, UserPrincipal userPrincipal);

    Idea addLook(long id, UserPrincipal userPrincipal);

    Idea addRating(long id, UserPrincipal userPrincipal);

    Idea reduceRating(long id, UserPrincipal userPrincipal);

    Idea changeStatus(long id, Status status, UserPrincipal userPrincipal);
}
