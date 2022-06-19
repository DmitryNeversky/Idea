package org.dneversky.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dneversky.gateway.security.Role;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private Set<Role> roles;
    private boolean enabled;

    private String name;
    private String phone;
    private Date birthday;
    private String avatar;
    private String city;
    private String about;
    private LocalDate registeredDate;
    private Post post;
}
