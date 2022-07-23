package org.dneversky.gateway.service;

import org.dneversky.gateway.security.UserPrincipal;

public interface DetailsService {
    UserPrincipal getUserPrincipalByUsername(String username);
}
