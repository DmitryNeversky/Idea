package org.dneversky.idea.repository;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.entity.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IdeaRepository extends MongoRepository<Idea, String> {

    @Query("{ 'tags._id': ?0 }")
    List<Idea> findAllByTag(String tagId);
}
