package org.dneversky.idea.payload;

import lombok.Data;
import org.dneversky.idea.entity.Post;

@Data
public class UserResponse {

    private Integer id;

    private String name;

    private Post post;
}
