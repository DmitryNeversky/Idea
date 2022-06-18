package org.dneversky.user.model;


import org.springframework.web.multipart.MultipartFile;

public class UpdateUserRequest {

    private String username;
    private UserRequest userRequest;
    private MultipartFile avatar;
    private boolean parseBoolean;
}
