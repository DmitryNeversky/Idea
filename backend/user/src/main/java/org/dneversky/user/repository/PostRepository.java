package org.dneversky.user.repository;

import org.dneversky.idea.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findByName(String name);
    boolean existsByName(String name);
}
