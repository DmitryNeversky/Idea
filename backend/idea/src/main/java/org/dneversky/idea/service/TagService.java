package org.dneversky.idea.service;

import org.dneversky.idea.entity.Tag;
import org.dneversky.idea.payload.TagRequest;

import java.util.List;

public interface TagService {

    List<Tag> getAllTags();

    Tag getTag(int id);

    Tag getTag(String name);

    Tag createTag(TagRequest requestTag);

    Tag updateTag(int id, TagRequest requestTag);

    void deleteTag(int id);
}
