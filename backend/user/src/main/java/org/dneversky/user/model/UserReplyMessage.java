package org.dneversky.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReplyMessage {

    private Long id;
    private String username;
    private String password;
    private boolean enabled;
}
