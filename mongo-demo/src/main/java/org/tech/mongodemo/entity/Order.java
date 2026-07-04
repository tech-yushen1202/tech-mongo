
package org.tech.mongodemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单实体类 - 演示MongoDB嵌入式文档和复杂结构
 * 包含嵌入式文档(items列表和shippingAddress)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    @Indexed
    @Field("userId")
    private String userId;

    @Field("orderNo")
    private String orderNo;

    @Field("items")
    private List<OrderItem> items;

    @Field("totalAmount")
    private BigDecimal totalAmount;

    @Field("status")
    private String status;

    @Field("shippingAddress")
    private Address shippingAddress;

    @Indexed
    @Field("orderDate")
    private LocalDateTime orderDate;

    @Field("createdAt")
    private LocalDateTime createdAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {
        @Field("productId")
        private String productId;

        @Field("quantity")
        private Integer quantity;

        @Field("price")
        private BigDecimal price;
    }

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
