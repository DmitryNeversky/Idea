package org.dneversky.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dneversky.gateway.security.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserModel {

    private String username;
    private UserPrincipal principal;
    private UserRequest userRequest;
    private MultipartFile avatar;
    private boolean parseBoolean;
}
