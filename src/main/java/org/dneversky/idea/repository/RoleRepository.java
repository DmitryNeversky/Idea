package org.dneversky.idea.repository;

import org.dneversky.idea.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, Integer> {
    Optional<Role> findByName(String name);
    boolean existsByName(String name);
}
