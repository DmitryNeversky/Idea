package org.dneversky.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReplyMessage implements Serializable {

    private Long id;
    private String username;
    private String password;
    private boolean enabled;
}
