package org.dneversky.idea.service;

import org.dneversky.idea.entity.Tag;
import org.dneversky.idea.payload.TagRequest;

import java.util.List;

public interface TagService {

    List<Tag> getAllTags();

    Tag getTagById(String id);

    Tag getTag(String name);

    Tag saveTag(TagRequest requestTag);

    Tag updateTag(String id, TagRequest requestTag);

    void deleteTag(String id);
}
