
package org.tech.mongodemo.service;

import org.tech.mongodemo.entity.Order;
import org.tech.mongodemo.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单服务接口 - 演示MongoDB聚合和复杂查询
 */
public interface OrderService {

    Order createOrder(Order order);

    Order getOrderById(String id);

    List<Order> getAllOrders();

    List<Order> getOrdersByUserId(String userId);

    List<Order> getOrdersByStatus(String status);

    List<Order> getOrdersByDateRange(LocalDateTime start, LocalDateTime end);

    List<Order> getOrdersContainingProduct(String productId);

    List<Order> getOrdersWithAmountGreaterThan(BigDecimal amount);

    List<OrderRepository.UserOrderStats> getUserOrderStats();

    List<OrderRepository.MonthlyStats> getMonthlyStats(LocalDateTime startDate);

    Order updateOrder(String id, Order order);

    void deleteOrder(String id);
}
