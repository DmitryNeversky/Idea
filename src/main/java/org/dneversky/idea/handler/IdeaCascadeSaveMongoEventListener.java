package org.dneversky.idea.handler;

import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

@Component
public class IdeaCascadeSaveMongoEventListener extends AbstractMongoEventListener<Idea> {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onAfterSave(AfterSaveEvent<Idea> event) {
        Idea idea = event.getSource();
        User author = idea.getAuthor();
        author.getIdeas().add(idea);
        mongoOperations.save(author);
    }
}
