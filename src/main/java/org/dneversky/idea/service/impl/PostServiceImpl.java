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
    public Post getPost(Integer id) {

        return postRepository.findById(id).get();
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
    public Post updatePost(Integer id, PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Post with id " + id + " not found."));

        post.setName(postRequest.getName());

        return postRepository.save(post);
    }

    @Override
    public void deletePost(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Post with id " + id + " not found."));

        if(post.getUsers() != null && post.getUsers().size() > 0) {
            post.getUsers().forEach(user -> {
                user.setPost(null);
                userRepository.save(user);
            });
        }

        postRepository.delete(post);
    }

    @PostConstruct
    private void init() {
        if(!postRepository.existsByName("Default")) {
            postRepository.save(new Post("Default"));
        }
    }
}
