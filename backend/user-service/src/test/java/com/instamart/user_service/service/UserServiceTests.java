package com.instamart.user_service.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.instamart.user_service.repository.UserRepository;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        assertNotNull(userRepository.findByUsername("test2"), "User not found");

    }
    @ParameterizedTest
    @EnumSource(UserName.class)
    public void testFindByUsername(UserName username) {
        assertNotNull(userRepository.findByUsername(username.name()), "User not found");
    }

}


enum UserName{
    TEST_2,
    ADMIN,
    TEST_3
}