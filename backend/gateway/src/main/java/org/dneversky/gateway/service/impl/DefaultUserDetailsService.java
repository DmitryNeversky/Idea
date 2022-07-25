package org.dneversky.gateway.service.impl;

import org.dneversky.gateway.api.client.GRPCUserClient;
import org.dneversky.gateway.security.UserPrincipal;
import org.dneversky.gateway.service.DetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserDetailsService implements UserDetailsService, DetailsService {

    private final GRPCUserClient userClient;

    public DefaultUserDetailsService(GRPCUserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userClient.getUserPrincipalByUsername(username);
    }

    @Override
    public UserPrincipal getUserPrincipalByUsername(String username) {
        return userClient.getUserPrincipalByUsername(username);
    }
}
