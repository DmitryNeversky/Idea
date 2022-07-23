package org.dneversky.gateway.converter;

import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.gateway.model.Post;

public final class PostConverter {

    public static UserServiceOuterClass.Post convert(Post post) {
        return UserServiceOuterClass.Post.newBuilder()
                .setId(post.getId())
                .setName(post.getName())
                .build();
    }

    public static Post convert(UserServiceOuterClass.Post post) {
        return Post.builder()
                .id(post.getId())
                .name(post.getName())
                .build();
    }
}
