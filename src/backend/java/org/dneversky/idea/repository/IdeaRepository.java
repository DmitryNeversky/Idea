package org.dneversky.idea.repository;

import org.dneversky.idea.model.Idea;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaRepository extends KeyValueRepository<Idea, String> {

}
