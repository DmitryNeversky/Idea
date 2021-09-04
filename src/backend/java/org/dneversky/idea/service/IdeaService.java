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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
//@CacheConfig(cacheNames = "idea")
public class IdeaService {

    @Value("${uploadPath}")
    private String UPLOAD_PATH;

    private final IdeaRepository ideaRepository;
    private final UserService userService;

//    @Cacheable
    public List<Idea> getAll() {

        return ideaRepository.findAll();
    }

//    @Cacheable(key = "#id")
    public Idea getIdeaById(int id) {
        Optional<Idea> findIdea = ideaRepository.findById(id);

        return findIdea.orElse(null);
    }

    public Idea saveIdea(Idea idea, List<MultipartFile> addImages, List<MultipartFile> addFiles, String username) {

        idea.setStatus(Status.LOOKING);
        idea.setCreatedDate(LocalDate.now());
        uploadImages(idea, addImages);
        uploadFiles(idea, addFiles);

        idea.setAuthor(userService.getUserByUsername(username));

        return ideaRepository.save(idea);
    }

//    @CachePut(key = "#idea.id")
    public Idea putIdea(Idea idea, List<MultipartFile> addImages, List<MultipartFile> addFiles) {

        removeImages(idea);
        removeFiles(idea);

        uploadImages(idea, addImages);
        uploadFiles(idea, addFiles);

        return ideaRepository.save(idea);
    }

//    @CacheEvict(key = "#id")
    public void delete(Idea idea) {
        Optional<Idea> findIdea = ideaRepository.findById(idea.getId());
        if(findIdea.isPresent())
            ideaRepository.delete(idea);

        removeImages(idea);
        removeFiles(idea);
    }

    private void uploadImages(Idea idea, List<MultipartFile> addImages) {
        if (addImages != null) {
            for (MultipartFile pair : addImages) {
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

    private void uploadFiles(Idea idea, List<MultipartFile> addFiles){
        if(addFiles != null) {
            for (MultipartFile pair : addFiles) {
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

    private void removeImages(Idea idea) {
        if (idea.getRemoveImages() != null) {
            for (String pair : idea.getRemoveImages()) {
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

    private void removeFiles(Idea idea) {
        if (idea.getRemoveFiles() != null) {
            System.out.println("!= null");
            for (String pair : idea.getRemoveFiles()) {
                System.out.println(pair);
                if (Files.exists(Paths.get(UPLOAD_PATH + "files/" + pair))) {
                    System.out.println("Exist");
                    try {
                        Files.delete(Paths.get(UPLOAD_PATH + "files/" + pair));
                        System.out.println(idea.getFiles());
                        System.out.println("removing " + pair);
                        idea.removeFile(pair);
                    } catch (IOException e) {
                        log.error("Removing idea's files error: {}", e.getMessage());
                    }
                }
            }
        }
    }

    public void addLook(Idea idea, String username) {
        User user = userService.getUserByUsername(username);
        if(!idea.getLookedUsers().contains(user)) {
            idea.addLook(user);
            ideaRepository.save(idea);
        }
    }

    public void addRating(Idea idea, String username) {
        User user = userService.getUserByUsername(username);
        if(idea.getRatedUsers().contains(user)) {
            idea.getRatedUsers().remove(user);
        } else {
            idea.getRatedUsers().add(user);
        }
        idea.getUnratedUsers().remove(user);
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
}
