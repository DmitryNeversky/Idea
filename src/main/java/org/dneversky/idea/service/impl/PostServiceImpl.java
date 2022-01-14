package org.dneversky.idea.service.impl;

import lombok.RequiredArgsConstructor;
import org.dneversky.idea.entity.Post;
import org.dneversky.idea.exception.EntityExistsException;
import org.dneversky.idea.exception.EntityNotFoundException;
import org.dneversky.idea.payload.PostRequest;
import org.dneversky.idea.repository.PostRepository;
import org.dneversky.idea.repository.UserRepository;
import org.dneversky.idea.service.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public List<Post> getAllPosts() {

        return postRepository.findAll();
    }

    @Override
    public Post getPost(String id) {

        return postRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Post with id " + id + " not found."));
    }

    @Override
    public Post savePost(PostRequest postRequest) {
        if(postRepository.existsByName(postRequest.getName())) {
            throw new EntityExistsException("Post with name " + postRequest.getName() + " already exists.");
        }

        Post post = new Post();
        post.setName(postRequest.getName());

        return postRepository.save(post);
    }

    @Override
    public Post updatePost(String id, PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Post with id " + id + " not found."));

        post.setName(postRequest.getName());

        return postRepository.save(post);
    }

    @Override
    public void deletePost(String id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Post with id " + id + " not found."));

        Optional<Post> findDefaultPost = postRepository.findByName("Default");
        if(!findDefaultPost.isPresent()) {
            postRepository.save(new Post("Default"));
        }

        Post defaultPost = postRepository.getByName("Default");

        // TODO: Single responsibility
        userRepository.saveAll(
                userRepository.findAllByPost(post)
                        .stream()
                        .peek(e -> e.setPost(defaultPost))
                        .collect(Collectors.toList())
        );

        postRepository.delete(post);
    }

    @PostConstruct
    private void init() {
        if(!postRepository.existsByName("Default")) {
            postRepository.save(new Post("Default"));
        }
    }
}
