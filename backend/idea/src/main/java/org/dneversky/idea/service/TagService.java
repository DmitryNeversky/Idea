package org.dneversky.idea.service;

import org.dneversky.idea.agregate.Tag;
import org.dneversky.idea.payload.TagRequest;

import java.util.List;

public interface TagService {

    List<Tag> getAllTags();

    Tag getTag(Integer id);

    Tag getTag(String name);

    Tag saveTag(TagRequest requestTag);

    Tag updateTag(Integer id, TagRequest requestTag);

    void deleteTag(Integer id);
}
