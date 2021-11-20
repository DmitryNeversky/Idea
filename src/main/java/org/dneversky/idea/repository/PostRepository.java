package org.dneversky.idea.repository;

import org.dneversky.idea.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, Integer> {
    Optional<Post> findByName(String name);
    boolean existsByName(String name);
}
