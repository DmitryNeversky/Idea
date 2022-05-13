package org.dneversky.gateway.security;

import lombok.RequiredArgsConstructor;
import org.dneversky.gateway.servie.impl.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPrincipal user = userService.getUserByUsername(username);

        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), null, user.isEnabled());
    }
}
