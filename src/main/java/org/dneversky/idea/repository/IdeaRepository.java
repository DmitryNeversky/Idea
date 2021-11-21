package org.dneversky.idea.repository;

import org.dneversky.idea.entity.Idea;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IdeaRepository extends MongoRepository<Idea, String> {

}
