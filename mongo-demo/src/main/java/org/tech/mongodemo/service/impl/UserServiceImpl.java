
package org.tech.mongodemo.service.impl;

import org.tech.mongodemo.entity.User;
import org.tech.mongodemo.repository.UserRepository;
import org.tech.mongodemo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户服务实现类 - 演示MongoDB CRUD操作实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        log.info("Creating user with username: {}", user.getUsername());
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id) {
        log.info("Getting user by id: {}", id);
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        log.info("Getting user by username: {}", username);
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("Getting user by email: {}", email);
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByAgeGreaterThan(Integer age) {
        log.info("Getting users with age greater than: {}", age);
        return userRepository.findByAgeGreaterThan(age);
    }

    @Override
    public List<User> getUsersByAgeRange(Integer minAge, Integer maxAge) {
        log.info("Getting users with age between {} and {}", minAge, maxAge);
        return userRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public List<User> getUsersByRole(String role) {
        log.info("Getting users with role: {}", role);
        return userRepository.findByRolesContaining(role);
    }

    @Override
    public List<User> getUsersByCity(String city) {
        log.info("Getting users from city: {}", city);
        return userRepository.findByCity(city);
    }

    @Override
    public User updateUser(String id, User user) {
        log.info("Updating user with id: {}", id);
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (user.getUsername() != null) {
                        existingUser.setUsername(user.getUsername());
                    }
                    if (user.getEmail() != null) {
                        existingUser.setEmail(user.getEmail());
                    }
                    if (user.getPassword() != null) {
                        existingUser.setPassword(user.getPassword());
                    }
                    if (user.getAge() != null) {
                        existingUser.setAge(user.getAge());
                    }
                    if (user.getAddress() != null) {
                        existingUser.setAddress(user.getAddress());
                    }
                    if (user.getRoles() != null) {
                        existingUser.setRoles(user.getRoles());
                    }
                    return userRepository.save(existingUser);
                })
                .orElse(null);
    }

    @Override
    public void deleteUser(String id) {
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public void deleteUserByUsername(String username) {
        log.info("Deleting user with username: {}", username);
        userRepository.deleteByUsername(username);
    }

    @Override
    public long countUsersByAgeGreaterThan(Integer age) {
        log.info("Counting users with age greater than: {}", age);
        return userRepository.countByAgeGreaterThan(age);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
