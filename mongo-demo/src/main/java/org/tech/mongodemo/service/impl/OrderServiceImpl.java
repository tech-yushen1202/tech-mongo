
package org.tech.mongodemo.service.impl;

import org.tech.mongodemo.entity.Order;
import org.tech.mongodemo.repository.OrderRepository;
import org.tech.mongodemo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单服务实现类 - 演示MongoDB聚合和复杂查询实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        log.info("Creating order: {}", order.getOrderNo());
        order.setCreatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(String id) {
        log.info("Getting order by id: {}", id);
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> getAllOrders() {
        log.info("Getting all orders");
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        log.info("Getting orders for user: {}", userId);
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        log.info("Getting orders with status: {}", status);
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<Order> getOrdersByDateRange(LocalDateTime start, LocalDateTime end) {
        log.info("Getting orders between {} and {}", start, end);
        return orderRepository.findByOrderDateBetween(start, end);
    }

    @Override
    public List<Order> getOrdersContainingProduct(String productId) {
        log.info("Getting orders containing product: {}", productId);
        return orderRepository.findOrdersContainingProduct(productId);
    }

    @Override
    public List<Order> getOrdersWithAmountGreaterThan(BigDecimal amount) {
        log.info("Getting orders with amount greater than: {}", amount);
        return orderRepository.findOrdersWithAmountGreaterThan(amount);
    }

    @Override
    public List<OrderRepository.UserOrderStats> getUserOrderStats() {
        log.info("Getting user order stats");
        return orderRepository.aggregateUserOrderStats();
    }

    @Override
    public List<OrderRepository.MonthlyStats> getMonthlyStats(LocalDateTime startDate) {
        log.info("Getting monthly stats from: {}", startDate);
        return orderRepository.aggregateMonthlyStats(startDate);
    }

    @Override
    public Order updateOrder(String id, Order order) {
        log.info("Updating order with id: {}", id);
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    if (order.getStatus() != null) {
                        existingOrder.setStatus(order.getStatus());
                    }
                    if (order.getShippingAddress() != null) {
                        existingOrder.setShippingAddress(order.getShippingAddress());
                    }
                    return orderRepository.save(existingOrder);
                })
                .orElse(null);
    }

    @Override
    public void deleteOrder(String id) {
        log.info("Deleting order with id: {}", id);
        orderRepository.deleteById(id);
    }
}
