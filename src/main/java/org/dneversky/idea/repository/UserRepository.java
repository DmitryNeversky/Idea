package org.dneversky.idea.repository;

import org.dneversky.idea.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    @Query("{ 'username' : {$regex: ?0, $options: 'i' }}")
    Optional<User> findByUsername(String username);
    User getByUsername(String username);
}
