package org.dneversky.idea.service;

import org.dneversky.idea.agregate.Idea;
import org.dneversky.idea.model.Status;
import org.dneversky.idea.payload.IdeaRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IdeaService {

    List<Idea> getAllIdeas();

    Page<Idea> getPagedIdeas(Integer page, Integer size, String sortDirection, String sortBy);

    Idea getIdea(Long id);

    Idea saveIdea(IdeaRequest ideaRequest, List<MultipartFile> addImages, List<MultipartFile> addFiles, Long currentUserId);

    Idea updateIdea(Long id, IdeaRequest ideaRequest, List<MultipartFile> addImages, List<MultipartFile> addFiles, Long currentUserId);

    void deleteIdea(Long id, Long currentUserId);

    Idea addLook(Long id, Long currentUserId);

    Idea addRating(Long id, Long currentUserId);

    Idea reduceRating(Long id, Long currentUserId);

    Idea changeStatus(Long id, Status status, Long currentUserId);
}
