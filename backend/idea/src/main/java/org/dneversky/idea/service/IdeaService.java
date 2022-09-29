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

    Page<Idea> getPagedIdeas(int page, int size, String sortDirection, String sortBy);

    Idea getIdea(long id);

    Idea createIdea(IdeaRequest ideaRequest, List<MultipartFile> addImages, List<MultipartFile> addFiles, UserPrincipal userPrincipal);

    Idea updateIdea(long id, IdeaRequest ideaRequest, List<MultipartFile> addImages, List<MultipartFile> addFiles, UserPrincipal userPrincipal);

    void deleteIdea(long id, UserPrincipal userPrincipal);

    Idea addLook(long id, UserPrincipal userPrincipal);

    Idea addRating(long id, UserPrincipal userPrincipal);

    Idea reduceRating(long id, UserPrincipal userPrincipal);

    Idea changeStatus(long id, Status status, UserPrincipal userPrincipal);
}
