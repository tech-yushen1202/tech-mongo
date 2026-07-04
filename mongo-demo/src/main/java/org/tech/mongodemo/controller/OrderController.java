
package org.tech.mongodemo.controller;

import org.tech.mongodemo.entity.Order;
import org.tech.mongodemo.repository.OrderRepository;
import org.tech.mongodemo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单控制器 - 演示MongoDB聚合和复杂查询的REST API
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        Order order = orderService.getOrderById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable String userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Order>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Order> orders = orderService.getOrdersByDateRange(start, end);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Order>> getOrdersContainingProduct(@PathVariable String productId) {
        List<Order> orders = orderService.getOrdersContainingProduct(productId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/amount/greater/{amount}")
    public ResponseEntity<List<Order>> getOrdersWithAmountGreaterThan(@PathVariable BigDecimal amount) {
        List<Order> orders = orderService.getOrdersWithAmountGreaterThan(amount);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/stats/user")
    public ResponseEntity<List<OrderRepository.UserOrderStats>> getUserOrderStats() {
        List<OrderRepository.UserOrderStats> stats = orderService.getUserOrderStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/stats/monthly")
    public ResponseEntity<List<OrderRepository.MonthlyStats>> getMonthlyStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {
        List<OrderRepository.MonthlyStats> stats = orderService.getMonthlyStats(startDate);
        return ResponseEntity.ok(stats);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable String id, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        return updatedOrder != null ? ResponseEntity.ok(updatedOrder) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
