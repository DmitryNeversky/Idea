package org.dneversky.idea.service;

import org.dneversky.idea.entity.Post;
import org.dneversky.idea.payload.PostRequest;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    Post getPost(String id);

    Post savePost(PostRequest postRequest);

    Post updatePost(String id, PostRequest postRequest);

    void deletePost(String id);
}
