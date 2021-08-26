package org.dneversky.idea.service;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.repository.IdeaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class IdeaServiceTest {

    private Idea idea;

    @Autowired
    private IdeaRepository ideaRepository;

    @BeforeAll
    void beforeAll() {
        idea = Idea.builder()
                .title("testTitle")
                .text("testText")
                .build();
    }

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void getAll() {
        ideaRepository.save(idea);
        assertTrue(ideaRepository.findAll().contains(idea));
    }

    @Test
    void get() {
        ideaRepository.save(idea);
        assertNotNull(ideaRepository.getOne(ideaRepository.findAll().get(0).getId()));
    }

    @Test
    void add() {
        assertNotNull(ideaRepository.save(idea));
    }

    @Test
    void put() {
        ideaRepository.save(idea);
        Idea findIdea = ideaRepository.getOne(1);
        ideaRepository.save(findIdea);
        assertEquals(1, ideaRepository.count());
    }

    @AfterEach
    void afterEach() {

    }
}