package org.dneversky.idea.service.impl;

import org.dneversky.idea.entity.Tag;
import org.dneversky.idea.exception.EntityExistsException;
import org.dneversky.idea.exception.EntityNotFoundException;
import org.dneversky.idea.payload.TagRequest;
import org.dneversky.idea.repository.IdeaRepository;
import org.dneversky.idea.repository.TagRepository;
import org.dneversky.idea.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final IdeaRepository ideaRepository;

    public TagServiceImpl(TagRepository tagRepository, IdeaRepository ideaRepository) {
        this.tagRepository = tagRepository;
        this.ideaRepository = ideaRepository;
    }

    @Override
    public List<Tag> getAllTags() {

        return tagRepository.findAll();
    }

    @Override
    public Tag getTagById(String id) {

        return tagRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Tag with id " + id + " not found in the database."));
    }

    @Override
    public Tag getTag(String name) {

        return tagRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("Tag with name " + name + " not found in the database."));
    }

    @Override
    public Tag saveTag(TagRequest tagRequest) {
        if(tagRepository.existsByName(tagRequest.getName())) {
            throw new EntityExistsException("Tag with name " + tagRequest.getName() + " already exists");
        }

        Tag tag = new Tag();
        tag.setName(tagRequest.getName());

        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(String id, TagRequest tagRequest) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Tag with id " + id + " not found in the database."));

        tag.setName(tagRequest.getName());

        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(String id) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Tag with id " + id + " not found in the database."));

        // TODO: Single responsibility
        ideaRepository.saveAll(
                ideaRepository.findAllByTag(id)
                .stream()
                    .peek(e -> e.getTags().remove(tag))
                    .collect(Collectors.toList())
        );

        tagRepository.delete(tag);
    }
}
