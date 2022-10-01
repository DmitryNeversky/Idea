package org.dneversky.idea.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dneversky.idea.client.EmailSender;
import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.exception.BadArgumentException;
import org.dneversky.idea.exception.PermissionException;
import org.dneversky.idea.model.EmailNotification;
import org.dneversky.idea.model.Status;
import org.dneversky.idea.payload.IdeaRequest;
import org.dneversky.idea.repository.IdeaRepository;
import org.dneversky.idea.repository.UserRepository;
import org.dneversky.idea.security.UserPrincipal;
import org.dneversky.idea.service.IdeaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;
    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final FileServiceImpl fileService;
    private final ImageServiceImpl imageService;

    @Override
    public Long getIdeaAmount() {
        return ideaRepository.count();
    }

    @Override
    public Page<Idea> getAllIdeas(int page, int size, String sortDirection, String sortBy) {
        if(page < 0) throw new BadArgumentException("Argument 'page' must not be less than 0.");
        if(size < 1) throw new BadArgumentException("Argument 'size' must not be less than 1.");
        if(!sortDirection.equals("ASC") && !sortDirection.equals("DESC"))
            throw new BadArgumentException("Argument 'direction' must be equals to 'ASC' or 'DESC'.");
        if(Arrays.stream(Idea.class.getDeclaredFields()).noneMatch(f -> f.getName().equals(sortBy)))
            throw new BadArgumentException("Argument 'sortBy' must to have existed field.");
        return ideaRepository.findAll(PageRequest.of(page, size, Sort.Direction.valueOf(sortDirection), sortBy));
    }

    @Override
    public List<Idea> getAllIdeas() {
        return ideaRepository.findAll();
    }

    @Override
    public Idea getIdea(long id) {
        return ideaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity Idea with id " + id + " not found."));
    }

    @Override
    public Idea createIdea(Idea idea, List<MultipartFile> attachedImages,
                           List<MultipartFile> attachedFiles, UserPrincipal principal) {
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new EntityNotFoundException("User with username " + principal.getUsername() + " not found."));

        idea.setStatus(Status.LOOKING);
        idea.setCreatedDate(LocalDate.now());

        user.getIdeas().add(idea);
        idea.setAuthor(user);
        idea.setImages(new HashSet<>(imageService.saveImages(attachedImages)));
        idea.setFiles(fileService.saveFiles(attachedFiles));

        return ideaRepository.save(idea);
    }

    @Override
    public Idea updateIdea(long id, IdeaRequest ideaRequest, List<MultipartFile> attachedImages, List<MultipartFile> attachedFiles, UserPrincipal principal) {
        Idea idea = ideaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity Idea with id " + id + " not found."));

        if(principal.getUsername().equals(idea.getAuthor().getUsername())) {
            idea.setTitle(ideaRequest.getTitle());
            idea.setBody(ideaRequest.getBody());
            idea.setTags(ideaRequest.getTags());

            List<String> removedImages = imageService.removeImages(ideaRequest.getRemoveImages());
            removedImages.forEach(idea.getImages()::remove);
            List<String> removedFiles = fileService.removeFiles(ideaRequest.getRemoveFiles());
            removedFiles.forEach(e -> idea.getFiles().remove(e));

            Set<String> savedImages = new HashSet<>(imageService.saveImages(attachedImages));
            idea.getImages().addAll(savedImages);
            idea.getFiles().putAll(fileService.saveFiles(attachedFiles));

            return ideaRepository.save(idea);
        }
        throw new PermissionException("You don't have permissions to update this Idea.");
    }

    @Override
    public void deleteIdea(long id, UserPrincipal principal) {
        Idea idea = ideaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity Idea with id " + id + " not found."));
        if(principal.getUsername().equals(idea.getAuthor().getUsername())) {
            imageService.removeImages(idea.getImages());
            fileService.removeFiles(idea.getFiles().keySet());
            idea.getAuthor().getIdeas().remove(idea);
            ideaRepository.delete(idea);
            return;
        }
        throw new PermissionException("You don't have permissions to delete this Idea.");
    }

    @Override
    public Idea changeStatus(long id, Status status, UserPrincipal principal) {
        Idea idea = ideaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity Idea with id " + id + " not found."));

            idea.setStatus(status);
            emailSender.sendMessage(new EmailNotification(idea.getAuthor().getUsername(),
                    "Status of your idea is changed",
                    "Dear " + idea.getAuthor().getName() + ", status of your idea with id " + id + " and title " + idea.getTitle() + " has been changed to " + status.getName() + "."));
            return ideaRepository.save(idea);
    }

    @Override
    public Idea addLook(long id, UserPrincipal principal) {
        Idea idea = ideaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity Idea with id " + id + " not found."));

        if(!idea.getLookedUsers().contains(principal.getId())) {
            idea.getLookedUsers().add(principal.getId());
            ideaRepository.save(idea);
        }

        return idea;
    }

    @Override
    public Idea addRating(long id, UserPrincipal principal) {
        Idea idea = ideaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity Idea with id " + id + " not found."));
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new EntityNotFoundException("User with username " + principal.getUsername() + " not found."));

        if(idea.getRatedUsers().contains(user)) {
            idea.getRatedUsers().remove(user);
        } else {
            idea.getRatedUsers().add(user);
        } idea.getUnratedUsers().remove(user);

        return ideaRepository.save(idea);
    }

    @Override
    public Idea reduceRating(long id, UserPrincipal principal) {
        Idea idea = ideaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity Idea with id " + id + " not found."));
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new EntityNotFoundException("User with username " + principal.getUsername() + " not found."));

        if(idea.getUnratedUsers().contains(user)) {
            idea.getUnratedUsers().remove(user);
        } else {
            idea.getUnratedUsers().add(user);
        }
        idea.getRatedUsers().remove(user);
        return ideaRepository.save(idea);
    }
}
