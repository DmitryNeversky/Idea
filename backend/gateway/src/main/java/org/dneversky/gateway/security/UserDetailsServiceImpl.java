package org.dneversky.gateway.security;

import lombok.RequiredArgsConstructor;
import org.dneversky.gateway.client.FeignUserClient;
import org.dneversky.gateway.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final FeignUserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userClient.getUserByUsername(username);
        return UserPrincipal.buildPrincipal(user);
    }
}
