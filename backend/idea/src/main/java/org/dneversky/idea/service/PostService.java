package org.dneversky.idea.service;

import org.dneversky.idea.entity.Post;
import org.dneversky.idea.payload.PostRequest;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    Post getPost(Integer id);

    Post savePost(PostRequest postRequest);

    Post updatePost(Integer id, PostRequest postRequest);

    void deletePost(Integer id);
}
