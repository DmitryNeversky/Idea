package org.dneversky.idea.service;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.repository.IdeaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdeaService {

    private final IdeaRepository ideaRepository;

    public IdeaService(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    public List<Idea> getAll() {

        return ideaRepository.findAll();
    }

    public Idea get(int id) {
        Optional<Idea> findIdea = ideaRepository.findById(id);
        return findIdea.orElse(null);

    }

    public Idea add(String title, String text) {
        // User requesting
        return ideaRepository.save(new Idea(title, text));
    }

    public Idea put(Idea idea) {
        Optional<Idea> findIdea = ideaRepository.findById(idea.getId());
        if(!findIdea.isPresent())
            return null;

        return ideaRepository.save(idea);
    }

    public void delete(Idea idea) {
        Optional<Idea> findIdea = ideaRepository.findById(idea.getId());
        if(findIdea.isPresent())
            ideaRepository.delete(idea);
    }
}
