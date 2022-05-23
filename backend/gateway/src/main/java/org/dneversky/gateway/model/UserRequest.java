package org.dneversky.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements Serializable {

    private Long id;
    private String username;
    private String password;
    private boolean enabled;

    public UserRequest(String username) {
        this.username = username;
    }
}
