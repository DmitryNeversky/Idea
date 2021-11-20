package org.dneversky.idea.service.impl;

import org.dneversky.idea.entity.Tag;
import org.dneversky.idea.exception.EntityExistsException;
import org.dneversky.idea.exception.EntityNotFoundException;
import org.dneversky.idea.payload.TagRequest;
import org.dneversky.idea.repository.IdeaRepository;
import org.dneversky.idea.repository.TagRepository;
import org.dneversky.idea.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Tag getTag(Integer id) {

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
    public Tag updateTag(Integer id, TagRequest tagRequest) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Tag with id " + id + " not found in the database."));

        tag.setName(tagRequest.getName());

        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Integer id) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Tag with id " + id + " not found in the database."));

        if(tag.getIdeas() != null && tag.getIdeas().size() > 0) {
            tag.getIdeas().forEach(idea -> {
                idea.getTags().remove(tag);
                ideaRepository.save(idea);
            });
        }
    }
}
