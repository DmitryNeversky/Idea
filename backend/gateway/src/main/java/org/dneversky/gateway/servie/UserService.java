package org.dneversky.gateway.servie;

import org.dneversky.gateway.model.User;

public interface UserService {

    User getUserByUsername(String username);
    User getCurrentUser();
}
