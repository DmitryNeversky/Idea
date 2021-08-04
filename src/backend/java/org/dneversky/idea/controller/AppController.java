package org.dneversky.idea.controller;

import org.dneversky.idea.model.Idea;
import org.dneversky.idea.model.User;
import org.dneversky.idea.repository.IdeaRepository;
import org.dneversky.idea.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AppController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final IdeaRepository ideaRepository;
    private final UserRepository userRepository;

    public AppController(RedisTemplate<String, Object> redisTemplate, IdeaRepository ideaRepository, UserRepository userRepository) {
        this.redisTemplate = redisTemplate;
        this.ideaRepository = ideaRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<Object> getPage(HttpServletRequest request){

        userRepository.save(new User(request.getSession().getId(), "1"));

        return new ResponseEntity<Object>(userRepository.findBySession(request.getSession().getId()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Idea> add(String title, String text){
        System.out.println(title + " " + text);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
