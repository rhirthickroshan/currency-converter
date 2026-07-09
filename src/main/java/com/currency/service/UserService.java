package com.currency.service;

import com.currency.entity.User;
import com.currency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public User createUser(User user) {

        logger.info("Create user request received.");

        logger.debug("Saving user with email: {}", user.getEmail());

        User savedUser = userRepository.save(user);

        logger.info("User created successfully with id: {}", savedUser.getId());

        return savedUser;
    }

    public List<User> getAllUsers() {

        logger.info("Fetching all users.");

        List<User> users = userRepository.findAll();

        logger.debug("Total users fetched: {}", users.size());

        return users;
    }

    public User getUserById(String id) {

        logger.info("Fetching user with id: {}", id);

        return userRepository.findById(id)
                .map(user -> {
                    logger.debug("User found with email: {}", user.getEmail());
                    return user;
                })
                .orElseThrow(() -> {
                    logger.warn("User not found with id: {}", id);
                    return new RuntimeException("User not found with id: " + id);
                });
    }

    public User updateUser(String id, User user) {

        logger.info("Update request received for user id: {}", id);

        User existingUser = getUserById(id);

        logger.debug("Updating user with email: {}", existingUser.getEmail());

        existingUser.setName(user.getName());
        existingUser.setAddress(user.getAddress());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        existingUser.setPassword(user.getPassword());

        User updatedUser = userRepository.save(existingUser);

        logger.info("User updated successfully with id: {}", id);

        return updatedUser;
    }

    public void deleteUser(String id) {

        logger.info("Delete request received for user id: {}", id);

        User existingUser = getUserById(id);

        logger.debug("Deleting user with email: {}", existingUser.getEmail());

        userRepository.delete(existingUser);

        logger.info("User deleted successfully with id: {}", id);
    }

    public User findByEmail(String email) {

        logger.debug("Searching user by email: {}", email);

        User user = userRepository.findByEmail(email);

        if (user == null) {
            logger.warn("No user found with email: {}", email);
        } else {
            logger.debug("User found with email: {}", email);
        }

        return user;
    }
}