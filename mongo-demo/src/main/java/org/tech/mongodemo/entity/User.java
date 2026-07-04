
package org.tech.mongodemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户实体类 - 演示MongoDB文档结构
 * 包含嵌入式文档(address)和数组字段(roles)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field("username")
    private String username;

    @Indexed(unique = true)
    @Field("email")
    private String email;

    @Field("password")
    private String password;

    @Field("age")
    private Integer age;

    @Field("address")
    private Address address;

    @Field("roles")
    private List<String> roles;

    @Field("createdAt")
    private LocalDateTime createdAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        @Field("city")
        private String city;

        @Field("street")
        private String street;

        @Field("zipCode")
        private String zipCode;
    }
}
