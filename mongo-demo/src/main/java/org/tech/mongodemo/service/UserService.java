
package org.tech.mongodemo.service;

import org.tech.mongodemo.entity.User;

import java.util.List;

/**
 * 用户服务接口 - 演示MongoDB CRUD操作
 */
public interface UserService {

    User createUser(User user);

    User getUserById(String id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<User> getUsersByAgeGreaterThan(Integer age);

    List<User> getUsersByAgeRange(Integer minAge, Integer maxAge);

    List<User> getUsersByRole(String role);

    List<User> getUsersByCity(String city);

    User updateUser(String id, User user);

    void deleteUser(String id);

    void deleteUserByUsername(String username);

    long countUsersByAgeGreaterThan(Integer age);

    boolean existsByEmail(String email);
}
