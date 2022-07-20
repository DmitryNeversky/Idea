package org.dneversky.user.converter;

import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.user.entity.Post;

public class PostConverter {

    public static UserServiceOuterClass.Post convert(Post post) {
        return UserServiceOuterClass.Post.newBuilder()
                .setId(post.getId())
                .setName(post.getName())
                .build();
    }
}
