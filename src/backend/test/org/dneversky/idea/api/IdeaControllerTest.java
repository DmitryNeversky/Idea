package org.dneversky.idea.api;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.repository.IdeaRepository;
import org.dneversky.idea.service.IdeaService;
import org.dneversky.idea.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IdeaControllerTest {

    private UserService userService;
    private IdeaRepository ideaRepository;
    private IdeaService ideaService;

    @BeforeAll
    void init() {

    }

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