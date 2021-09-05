package org.dneversky.idea.api;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.service.IdeaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IdeaControllerTest {

    @Autowired
    private IdeaService ideaService;

    @Test
    void ideasEmptyIfNoIdeaAdded() {
        List<Idea> ideas = ideaService.getIdeas();
        assertTrue(ideas.isEmpty());
    }

    @Test
    void getIdeaById() {
    }

    @Test
    void saveIdea() {
    }

    @Test
    void putIdea() {
    }

    @Test
    void deleteIdea() {
    }

    @Test
    void addLook() {
    }

    @Test
    void addRating() {
    }

    @Test
    void reduceRating() {
    }
}