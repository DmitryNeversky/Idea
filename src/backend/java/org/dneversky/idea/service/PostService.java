package org.dneversky.idea.service;

import lombok.RequiredArgsConstructor;
import org.dneversky.idea.entity.Post;
import org.dneversky.idea.repository.PostRepository;
import org.dneversky.idea.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<Post> getPosts() {

        return postRepository.findAll();
    }

    public Post savePost(Post post) {
        if(postRepository.findByName(post.getName()) != null) {
            return null;
        }

        return postRepository.save(post);
    }

    public Post putPost(Post post) {
        Optional<Post> findPost = postRepository.findById(post.getId());
        if(!findPost.isPresent())
            return null;

        findPost.get().setName(post.getName());

        return  postRepository.save(findPost.get());
    }

    public void deletePost(Post post) {
        userRepository.findAll().forEach(u -> u.setPost(null));

        postRepository.delete(post);
    }

    @PostConstruct
    private void init() {
        if(postRepository.findByName("Default") == null)
            postRepository.save(new Post("Default"));
    }
}
