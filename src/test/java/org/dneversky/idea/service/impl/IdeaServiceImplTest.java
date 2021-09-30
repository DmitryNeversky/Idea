package org.dneversky.idea.service.impl;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.payload.IdeaRequest;
import org.dneversky.idea.repository.IdeaRepository;
import org.dneversky.idea.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({
        MockitoExtension.class
})
class IdeaServiceImplTest {

    @Captor
    private ArgumentCaptor<Idea> argumentCaptor;
    @Mock
    private IdeaRepository ideaRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private IdeaServiceImpl ideaService;

    @BeforeAll
    private void init() {

    }

    @Test
    void getAllIdeas() {
        List<Idea> ideas = ideaService.getAllIdeas();

        verify(ideaRepository).findAll();
    }

    @Test
    void throwExceptionIfIdeaNotAdded() {

        verify(ideaRepository).save(Mockito.any());
    }

    @Test
    void saveIdea() {
    }

    @Test
    @Disabled
    void updateIdea() {
    }

    @Test
    @Disabled
    void deleteIdea() {
    }

    @Test
    @Disabled
    void changeStatus() {
    }

    @Test
    @Disabled
    void addLook() {
    }

    @Test
    @Disabled
    void addRating() {
    }

    @Test
    @Disabled
    void reduceRating() {
    }
}