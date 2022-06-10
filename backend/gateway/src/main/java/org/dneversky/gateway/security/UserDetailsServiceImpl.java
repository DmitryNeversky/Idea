package org.dneversky.gateway.security;

import lombok.RequiredArgsConstructor;
import org.dneversky.gateway.model.User;
import org.dneversky.gateway.client.UserClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserClient userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        return UserPrincipal.buildPrincipal(user);
    }
}
