package org.dneversky.idea.repository;

import org.dneversky.idea.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{ 'username' : {$regex: ?0, $options: 'i' }}")
    Optional<User> findByUsername(String username);
    User getByUsername(String username);
}
