package org.dneversky.gateway.security;

import lombok.RequiredArgsConstructor;
import org.dneversky.gateway.model.User;
import org.dneversky.gateway.servie.UserService;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);

        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getRoles(), user.isEnabled());
    }
}
