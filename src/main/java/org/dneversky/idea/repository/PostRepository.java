package org.dneversky.idea.repository;

import org.dneversky.idea.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String> {
    Optional<Post> findByName(String name);
    Post getByName(String name);
    boolean existsByName(String name);
}
