package org.dneversky.gateway.repository;

import org.dneversky.gateway.security.UserPrincipal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReplicatedUserRepository extends CrudRepository<UserPrincipal, Long> {
    Optional<UserPrincipal> findByUsername(String username);
}
