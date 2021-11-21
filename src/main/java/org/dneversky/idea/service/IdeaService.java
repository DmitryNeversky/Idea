package org.dneversky.idea.service;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.model.Status;
import org.dneversky.idea.payload.IdeaRequest;
import org.dneversky.idea.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IdeaService {

    List<Idea> getAllIdeas();

    Page<Idea> getPagedIdeas(Integer page, Integer size, String sortDirection, String sortBy);

    Idea getIdea(String id);

    Idea saveIdea(IdeaRequest ideaRequest, List<MultipartFile> addImages, List<MultipartFile> addFiles, UserPrincipal userPrincipal);

    Idea updateIdea(String id, IdeaRequest ideaRequest, List<MultipartFile> addImages, List<MultipartFile> addFiles, UserPrincipal userPrincipal);

    void deleteIdea(String id, UserPrincipal userPrincipal);

    Idea addLook(String id, UserPrincipal userPrincipal);

    Idea addRating(String id, UserPrincipal userPrincipal);

    Idea reduceRating(String id, UserPrincipal userPrincipal);

    Idea changeStatus(String id, Status status, UserPrincipal userPrincipal);
}
