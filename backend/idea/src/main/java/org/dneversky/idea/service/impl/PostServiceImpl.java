package org.dneversky.idea.service.impl;

import org.dneversky.idea.entity.Post;
import org.dneversky.idea.payload.PostRequest;
import org.dneversky.idea.repository.PostRepository;
import org.dneversky.idea.repository.UserRepository;
import org.dneversky.idea.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPost(int id) {
        return postRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Post with id " + id + " not found."));
    }

    @Override
    public Post getPost(String name) {
        return postRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("Post with name " + name + " not found."));
    }

    @Override
    public Post createPost(PostRequest postRequest) {
        if(postRepository.findByName(postRequest.getName()).isPresent())
            throw new EntityExistsException("Post with name " + postRequest.getName() + " already exists.");
        Post post = new Post();
        post.setName(postRequest.getName());
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(int id, PostRequest postRequest) {
        Post post = getPost(id);
        post.setName(postRequest.getName());
        return postRepository.save(post);
    }

    @Override
    public void deletePost(int id) {
        Post post = getPost(id);
        detachUsers(post);
        postRepository.delete(post);
    }

    private void detachUsers(Post post) {
        if(post.getUsers() != null && post.getUsers().size() > 0) {
            Post defaultPost = postRepository.findByName("Default").orElse(null);
            post.getUsers().forEach(user -> {
                user.setPost(defaultPost);
                userRepository.save(user);
            });
        }
    }

    @PostConstruct
    private void init() {
        if(!postRepository.existsByName("Default")) {
            postRepository.save(new Post("Default"));
        }
    }
}
