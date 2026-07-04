
package org.tech.mongodemo.repository;

import org.tech.mongodemo.entity.Order;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单数据访问层 - 演示MongoDB聚合和复杂查询
 * 包含聚合管道、嵌入式文档查询等
 */
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserId(String userId);

    List<Order> findByStatus(String status);

    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("{ 'items.productId': ?0 }")
    List<Order> findOrdersContainingProduct(String productId);

    @Query("{ 'totalAmount': { $gte: ?0 } }")
    List<Order> findOrdersWithAmountGreaterThan(BigDecimal amount);

    @Aggregation(pipeline = {
            "{ '$group': { '_id': '$userId', 'totalOrders': { '$sum': 1 }, 'totalAmount': { '$sum': '$totalAmount' } } }",
            "{ '$sort': { 'totalAmount': -1 } }"
    })
    List<UserOrderStats> aggregateUserOrderStats();

    @Aggregation(pipeline = {
            "{ '$match': { 'orderDate': { '$gte': ?0 } } }",
            "{ '$group': { '_id': { '$month': '$orderDate' }, 'totalAmount': { '$sum': '$totalAmount' }, 'orderCount': { '$sum': 1 } } }",
            "{ '$sort': { '_id': 1 } }"
    })
    List<MonthlyStats> aggregateMonthlyStats(LocalDateTime startDate);

    interface UserOrderStats {
        String getUserId();
        Long getTotalOrders();
        BigDecimal getTotalAmount();
    }

    interface MonthlyStats {
        Integer getId();
        BigDecimal getTotalAmount();
        Long getOrderCount();
    }
}
