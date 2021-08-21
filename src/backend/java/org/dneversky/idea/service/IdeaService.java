package org.dneversky.idea.service;

import org.dneversky.idea.model.Idea;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IdeaService {

    @Value("${uploadPath}")
    private String uploadPath;

    private final IdeaRepository ideaRepository;

    public IdeaService(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    public List<Idea> getAll() {

        return (List<Idea>) ideaRepository.findAll();
    }

    public Idea get(String id) {
        Optional<Idea> findIdea = ideaRepository.findById(id);

        return findIdea.orElse(null);
    }

    public Idea add(String title, String text, List<String> tags, List<MultipartFile> images, List<MultipartFile> files) {
        // User requesting

        Idea idea = new Idea(title, text, Status.LOOKING, LocalDateTime.now());
        if(tags != null)
            idea.setTags(tags);
        if(images != null)
            uploadImages(idea, images);
        if(files != null)
            uploadFiles(idea, files);

        return ideaRepository.save(idea);
    }

    public Idea put(Idea idea, String title, String text, List<String> tags, List<MultipartFile> addImages,
                    List<String> removeImages, List<MultipartFile> addFiles, List<String> removeFiles) {
        Optional<Idea> findIdea = ideaRepository.findById(idea.getId());
        if(!findIdea.isPresent())
            return null;

        idea.setTitle(title);
        idea.setText(text);
        idea.setTags(tags);

        if (removeImages != null) {
            removeImages.forEach(x -> {
                if (Files.exists(Paths.get(uploadPath + "images/" + x))) {
                    try {
                        Files.delete(Paths.get(uploadPath + "images/" + x));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                idea.getImages().remove(x);
            });
        }

        if (removeFiles != null) {
            removeFiles.forEach(x -> {
                if (Files.exists(Paths.get(uploadPath + "files/" + x))) {
                    try {
                        Files.delete(Paths.get(uploadPath + "files/" + x));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                idea.getFiles().remove(x);
            });
        }

        uploadImages(idea, addImages);
        uploadFiles(idea, addFiles);

        return ideaRepository.save(idea);
    }

    public void delete(Idea idea) {
        Optional<Idea> findIdea = ideaRepository.findById(idea.getId());
        if(findIdea.isPresent())
            ideaRepository.delete(idea);

        if (idea.getImages() != null) {
            for (String pair : idea.getImages()) {
                if (Files.exists(Paths.get(uploadPath + "images/" + pair))) {
                    try {
                        Files.delete(Paths.get(uploadPath + "images/" + pair));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (idea.getFiles() != null) {
            for (String pair : idea.getFiles()) {
                if (Files.exists(Paths.get(uploadPath + "files/" + pair))) {
                    try {
                        Files.delete(Paths.get(uploadPath + "files/" + pair));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void uploadImages(Idea idea, List<MultipartFile> images) {
        if (images != null) {
            for (MultipartFile pair : images) {
                if (Objects.requireNonNull(pair.getOriginalFilename()).isEmpty())
                    continue;

                String fileName = java.util.UUID.randomUUID() + "_"
                        + StringUtils.cleanPath(Objects.requireNonNull(pair.getOriginalFilename()));

                try {
                    Path path = Paths.get(uploadPath + "/images/" + fileName);
                    Files.copy(pair.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    idea.addImage(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void uploadFiles(Idea idea, List<MultipartFile> files){

        if(files != null) {
            for (MultipartFile pair : files) {
                if (Objects.requireNonNull(pair.getOriginalFilename()).isEmpty())
                    continue;

                String fileName = java.util.UUID.randomUUID() + "_"
                        + StringUtils.cleanPath(Objects.requireNonNull(pair.getOriginalFilename()));

                try {
                    Path path = Paths.get(uploadPath + "files/" + fileName);
                    Files.copy(pair.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    idea.addFile(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
