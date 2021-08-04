package org.dneversky.idea.repository;

import org.dneversky.idea.model.User;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRepository {

    private final RedisOperations<String, String> redisOperations;

    public UserRepository(RedisOperations<String, String> redisOperations) {
        this.redisOperations = redisOperations;
    }

    public User findBySession(String sessionID) {
        String id = redisOperations.opsForValue().get(sessionID);

        return new User(sessionID, id);
    }

    public void save(User user) {
        redisOperations.opsForValue().set(user.getSessionID(), user.getId());
    }
}
