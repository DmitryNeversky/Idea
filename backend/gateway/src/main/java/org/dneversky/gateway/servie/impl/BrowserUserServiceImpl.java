package org.dneversky.gateway.servie.impl;

import org.dneversky.gateway.model.User;
import org.dneversky.gateway.servie.UserService;
import org.springframework.stereotype.Service;

@Service
public class BrowserUserServiceImpl implements UserService {
    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
