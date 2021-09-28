package org.dneversky.idea.payload;

import lombok.Data;
import org.dneversky.idea.entity.Post;

@Data
public class UserRequest {

    private String name;
    private String phone;
    private String about;
    private String city;

    private Post post;
}
