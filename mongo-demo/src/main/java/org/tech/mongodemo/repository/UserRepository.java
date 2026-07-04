
package org.tech.mongodemo.repository;

import org.tech.mongodemo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户数据访问层 - 演示MongoDB基本查询
 * 包含派生查询和自定义查询示例
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByAgeGreaterThan(Integer age);

    List<User> findByAgeBetween(Integer minAge, Integer maxAge);

    List<User> findByRolesContaining(String role);

    @Query("{ 'address.city': ?0 }")
    List<User> findByCity(String city);

    @Query("{ 'username': { $regex: ?0, $options: 'i' } }")
    List<User> findByUsernameLike(String pattern);

    @Query(value = "{ 'age': { $gt: ?0 } }", fields = "{ 'username': 1, 'email': 1 }")
    List<User> findUsernamesByAgeGreaterThan(Integer age);

    long countByAgeGreaterThan(Integer age);

    boolean existsByEmail(String email);

    void deleteByUsername(String username);
}
