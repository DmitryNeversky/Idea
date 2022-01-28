package org.dneversky.idea.service.impl;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.exception.EntityNotFoundException;
import org.dneversky.idea.repository.IdeaRepository;
import org.dneversky.idea.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class IdeaServiceImplTest {

    @Spy
    private IdeaRepository ideaRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private IdeaServiceImpl ideaService;

    @Test
    void getAllIdeas() {
        when(ideaService.getAllIdeas()).thenReturn(new ArrayList<>());
        ideaService.getAllIdeas();
        verify(ideaRepository, only()).findAll();
    }

    @Test
    void getExistedIdeaById() {
        var id = "1";

        given(ideaRepository.findById(anyString())).willReturn(Optional.of(new Idea()));
        ideaService.getIdea(id);
        then(ideaRepository).should(only()).findById(id);
    }

    @Test
    void throwWhenGetNotExistedIdeaById() {
        var id = "1";

        given(ideaRepository.findById(anyString())).willReturn(Optional.empty());
        assertThatThrownBy(() -> ideaService.getIdea(id)).isInstanceOf(EntityNotFoundException.class);
        then(ideaRepository).should().findById(id);
    }
}