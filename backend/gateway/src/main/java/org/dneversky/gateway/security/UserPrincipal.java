package org.dneversky.gateway.security;

import org.dneversky.gateway.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private final Long id;

    private final String username;
    private final String password;
    private final Set<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    public UserPrincipal(Long id, String username, String password, Set<? extends GrantedAuthority> authorities, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public static UserPrincipal buildPrincipal(User user) {
        Set<SimpleGrantedAuthority> authorities1 = new HashSet<>();
        if(user.getRoles() != null) {
            authorities1 = user.getRoles().stream()
                    .map(e -> new SimpleGrantedAuthority(e.getName()))
                    .collect(Collectors.toSet());
        }
        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), authorities1, user.isEnabled());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getId() {
        return id;
    }
}
