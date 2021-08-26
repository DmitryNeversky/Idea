package org.dneversky.idea.service;

import com.sun.xml.internal.ws.api.server.InstanceResolverAnnotation;
import org.dneversky.idea.entity.Role;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.repository.RoleRepository;
import org.dneversky.idea.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void addRoleToUser() {

    }
}