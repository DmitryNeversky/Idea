package org.dneversky.idea.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.model.Status;
import org.dneversky.idea.repository.IdeaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IdeaService {

    @Value("${uploadPath}")
    private String UPLOAD_PATH;

    private final IdeaRepository ideaRepository;
    private final UserService userService;

    public List<Idea> getIdeas() {

        return ideaRepository.findAll();
    }

    public Idea getIdeaById(int id) {
        Optional<Idea> findIdea = ideaRepository.findById(id);

        return findIdea.orElse(null);
    }

    public Idea saveIdea(Idea idea, List<MultipartFile> addImages, List<MultipartFile> addFiles, String username) {

        idea.setStatus(Status.LOOKING);
        idea.setCreatedDate(LocalDate.now());
        uploadImages(idea, addImages);
        uploadFiles(idea, addFiles);

        User user = userService.getUserByUsername(username);
        user.addIdea(idea);

        idea.setAuthor(user);

        return ideaRepository.save(idea);
    }

    public Idea putIdea(Idea idea, List<MultipartFile> addImages, List<MultipartFile> addFiles) {
        Optional<Idea> findIdea = ideaRepository.findById(idea.getId());
        if(!findIdea.isPresent()) {
            return null;
        }

        removeImages(idea, idea.getRemoveImages());
        removeFiles(idea, idea.getRemoveFiles());
        uploadImages(idea, addImages);
        uploadFiles(idea, addFiles);

        idea.setLookedUsers(findIdea.get().getLookedUsers());

        return ideaRepository.save(idea);
    }

    public void deleteIdea(int id) {
        Optional<Idea> idea = ideaRepository.findById(id);
        if(!idea.isPresent())
            return;

        removeImages(idea.get(), idea.get().getImages());
        removeFiles(idea.get(), idea.get().getFiles().keySet());

        ideaRepository.delete(idea.get());
    }

    public void addLook(Idea idea, String username) {
        User user = userService.getUserByUsername(username);
        if(!idea.getLookedUsers().contains(user.getId())) {
            idea.addLook(user.getId());

            ideaRepository.save(idea);
        }
    }

    public void addRating(Idea idea, String username) {
        User user = userService.getUserByUsername(username);
        if(idea.getRatedUsers().contains(user)) {
            idea.getRatedUsers().remove(user);
        } else {
            idea.getRatedUsers().add(user);
        } idea.getUnratedUsers().remove(user);

        ideaRepository.save(idea);
    }

    public void reduceRating(Idea idea, String username) {
        User user = userService.getUserByUsername(username);
        if(idea.getUnratedUsers().contains(user)) {
            idea.getUnratedUsers().remove(user);
        } else {
            idea.getUnratedUsers().add(user);
        }
        idea.getRatedUsers().remove(user);

        ideaRepository.save(idea);
    }

    private void uploadImages(Idea idea, Collection<MultipartFile> images) {
        if (images != null) {
            for (MultipartFile pair : images) {
                if (Objects.requireNonNull(pair.getOriginalFilename()).isEmpty())
                    continue;
                String fileName = java.util.UUID.randomUUID() + "_"
                        + StringUtils.cleanPath(Objects.requireNonNull(pair.getOriginalFilename()));
                try {
                    Path path = Paths.get(UPLOAD_PATH + "/images/" + fileName);
                    Files.copy(pair.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    idea.addImage(fileName);
                } catch (IOException e) {
                    log.error("Uploading idea's images error: {}", e.getMessage());
                }
            }
        }
    }

    private void uploadFiles(Idea idea, Collection<MultipartFile> files){
        if(files != null) {
            for (MultipartFile pair : files) {
                if (Objects.requireNonNull(pair.getOriginalFilename()).isEmpty())
                    continue;
                String fileName = java.util.UUID.randomUUID() + "_"
                        + StringUtils.cleanPath(Objects.requireNonNull(pair.getOriginalFilename()));
                try {
                    Path path = Paths.get(UPLOAD_PATH + "files/" + fileName);
                    Files.copy(pair.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    idea.addFile(fileName, pair.getOriginalFilename());
                } catch (IOException e) {
                    log.error("Uploading idea's files error: {}", e.getMessage());
                }
            }
        }
    }

    private void removeImages(Idea idea, Collection<String> images) {
        if (images != null) {
            for (String pair : images) {
                if (Files.exists(Paths.get(UPLOAD_PATH + "images/" + pair))) {
                    try {
                        Files.delete(Paths.get(UPLOAD_PATH + "images/" + pair));
                        idea.removeImage(pair);
                    } catch (IOException e) {
                        log.error("Removing idea's images error: {}", e.getMessage());
                    }
                }
            }
        }
    }

    private void removeFiles(Idea idea, Collection<String> files) {
        if (files != null) {
            for (String pair : files) {
                if (Files.exists(Paths.get(UPLOAD_PATH + "files/" + pair))) {
                    try {
                        Files.delete(Paths.get(UPLOAD_PATH + "files/" + pair));
                        idea.removeFile(pair);
                    } catch (IOException e) {
                        log.error("Removing idea's files error: {}", e.getMessage());
                    }
                }
            }
        }
    }
}
