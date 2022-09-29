package org.dneversky.idea.service;

import org.dneversky.idea.entity.Post;
import org.dneversky.idea.payload.PostRequest;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    Post getPost(int id);

    Post createPost(PostRequest postRequest);

    Post updatePost(int id, PostRequest postRequest);

    void deletePost(int id);
}
