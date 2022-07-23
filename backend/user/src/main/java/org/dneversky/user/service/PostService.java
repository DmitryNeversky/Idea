package org.dneversky.user.service;

import org.dneversky.user.entity.Post;
import org.dneversky.user.dto.PostRequest;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    Post getPost(Integer id);

    Post savePost(PostRequest postRequest);

    Post updatePost(Integer id, PostRequest postRequest);

    void deletePost(Integer id);
}
