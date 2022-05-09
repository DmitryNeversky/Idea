package org.dneversky.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dneversky.gateway.security.Role;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;
    private String password;
    private boolean enabled;
    private Set<Role> roles = new HashSet<>();

}
