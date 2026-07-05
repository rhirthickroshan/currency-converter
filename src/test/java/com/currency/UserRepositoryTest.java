//package com.currency;
//
//import com.currency.entity.Roles;
//import com.currency.entity.User;
//import com.currency.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.testcontainers.containers.MongoDBContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//@Testcontainers
//class UserRepositoryTest {
//
//    @Container
//    @ServiceConnection
//    static MongoDBContainer mongoDBContainer =
//            new MongoDBContainer("mongo:7.0");
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void cleanUp() {
//        userRepository.deleteAll();
//    }
//
//    @Test
//    void shouldSaveUser() {
//        User user = new User();
//        user.setName("Hirthick");
//        user.setEmail("hirthick@gmail.com");
//        user.setPassword("12345");
//        user.setRole(Roles.USER);
//
//        User savedUser = userRepository.save(user);
//
//        assertNotNull(savedUser.getId());
//        assertEquals("hirthick@gmail.com", savedUser.getEmail());
//    }
//
//    @Test
//    void shouldFindByEmail() {
//        User user = new User();
//        user.setName("Hirthick");
//        user.setEmail("hirthick@gmail.com");
//        user.setPassword("12345");
//        user.setRole(Roles.USER);
//
//        userRepository.save(user);
//
//        User result = userRepository.findByEmail("hirthick@gmail.com");
//
//        assertNotNull(result);
//        assertEquals("Hirthick", result.getName());
//    }
//}