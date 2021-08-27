package org.dneversky.idea.api;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

@RequestMapping("api")
@RestController
public class TagController {

    private final RedisTemplate<String, String> redisTemplate;

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOperations;

    public TagController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/tags")
    public ResponseEntity<Set<String>> getAll() {
        setOperations.add("tags", "Banana");
        return new ResponseEntity<>(setOperations.members("tags"), HttpStatus.OK);
    }
}
