
package org.tech.mongodemo;

import org.tech.mongodemo.entity.Product;
import org.tech.mongodemo.entity.User;
import org.junit.jupiter.api.Order;
import org.tech.mongodemo.repository.OrderRepository;
import org.tech.mongodemo.repository.ProductRepository;
import org.tech.mongodemo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * MongoDB Demo Application 测试类
 * 演示MongoDB基本功能的单元测试
 * 
 * 测试顺序：
 * 1. testUserCrud - 用户CRUD操作测试
 * 2. testUserQueries - 用户查询功能测试
 * 3. testProductCrud - 产品CRUD操作测试
 * 4. testProductQueries - 产品查询和分页测试
 * 5. testOrderCrud - 订单CRUD操作测试
 * 6. testOrderAggregation - 订单聚合查询测试
 */
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MongoDemoApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 测试用户CRUD操作
     * 演示：创建、查询、更新、删除用户
     */
    @Test
    @Order(1)
    void testUserCrud() {
        log.info("=== 开始测试用户CRUD操作 ===");

        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .age(25)
                .address(User.Address.builder()
                        .city("Beijing")
                        .street("Test Street")
                        .zipCode("100000")
                        .build())
                .roles(Arrays.asList("user"))
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);
        log.info("创建用户成功: {}", savedUser.getUsername());

        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        assert foundUser != null;
        assert foundUser.getUsername().equals("testuser");
        log.info("查询用户成功: {}", foundUser.getEmail());

        foundUser.setAge(26);
        User updatedUser = userRepository.save(foundUser);
        assert updatedUser.getAge().equals(26);
        log.info("更新用户成功: 年龄更新为 {}", updatedUser.getAge());

        userRepository.deleteById(savedUser.getId());
        assert userRepository.findById(savedUser.getId()).isEmpty();
        log.info("删除用户成功");

        log.info("=== 用户CRUD操作测试完成 ===");
    }

    /**
     * 测试用户查询功能
     * 演示：派生查询、自定义查询、正则查询、统计查询
     */
    @Test
    @Order(2)
    void testUserQueries() {
        log.info("=== 开始测试用户查询功能 ===");

        List<User> usersOver30 = userRepository.findByAgeGreaterThan(30);
        log.info("年龄大于30的用户数量: {}", usersOver30.size());

        List<User> usersInRange = userRepository.findByAgeBetween(20, 30);
        log.info("年龄在20-30之间的用户数量: {}", usersInRange.size());

        List<User> adminUsers = userRepository.findByRolesContaining("admin");
        log.info("包含admin角色的用户数量: {}", adminUsers.size());

        List<User> usersInBeijing = userRepository.findByCity("Beijing");
        log.info("北京用户数量: {}", usersInBeijing.size());

        long countOver25 = userRepository.countByAgeGreaterThan(25);
        log.info("年龄大于25的用户数量: {}", countOver25);

        boolean exists = userRepository.existsByEmail("zhangsan@example.com");
        log.info("zhangsan@example.com 是否存在: {}", exists);

        log.info("=== 用户查询功能测试完成 ===");
    }

    /**
     * 测试产品CRUD操作
     * 演示：创建、查询、更新、删除产品
     */
    @Test
    @Order(3)
    void testProductCrud() {
        log.info("=== 开始测试产品CRUD操作 ===");

        Product product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .category("Test")
                .stock(100)
                .tags(Arrays.asList("test", "demo"))
                .createdAt(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(product);
        log.info("创建产品成功: {}", savedProduct.getName());

        Product foundProduct = productRepository.findById(savedProduct.getId()).orElse(null);
        assert foundProduct != null;
        assert foundProduct.getName().equals("Test Product");
        log.info("查询产品成功: {}", foundProduct.getDescription());

        foundProduct.setPrice(new BigDecimal("199.99"));
        Product updatedProduct = productRepository.save(foundProduct);
        assert updatedProduct.getPrice().equals(new BigDecimal("199.99"));
        log.info("更新产品成功: 价格更新为 {}", updatedProduct.getPrice());

        productRepository.deleteById(savedProduct.getId());
        assert productRepository.findById(savedProduct.getId()).isEmpty();
        log.info("删除产品成功");

        log.info("=== 产品CRUD操作测试完成 ===");
    }

    /**
     * 测试产品查询和分页功能
     * 演示：分类查询、价格范围查询、分页、文本搜索、标签查询
     */
    @Test
    @Order(4)
    void testProductQueries() {
        log.info("=== 开始测试产品查询功能 ===");

        List<Product> electronics = productRepository.findByCategory("Electronics");
        log.info("电子产品数量: {}", electronics.size());

        List<Product> priceRange = productRepository.findByPriceBetween(
                new BigDecimal("1000"), new BigDecimal("10000"));
        log.info("价格在1000-10000之间的产品数量: {}", priceRange.size());

        var pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "price"));
        var page = productRepository.findByCategory("Electronics", pageable);
        log.info("电子产品分页查询 - 总数: {}, 当前页数量: {}", page.getTotalElements(), page.getNumberOfElements());

        List<Product> searchResult = productRepository.searchByText("Apple");
        log.info("搜索 'Apple' 结果数量: {}", searchResult.size());

        List<Product> taggedProducts = productRepository.findByTagsContaining(Arrays.asList("apple"));
        log.info("包含 'apple' 标签的产品数量: {}", taggedProducts.size());

        long count = productRepository.countByCategory("Electronics");
        log.info("电子产品总数: {}", count);

        log.info("=== 产品查询功能测试完成 ===");
    }

    /**
     * 测试订单CRUD操作
     * 演示：创建、查询、更新、删除订单（包含嵌入式文档）
     */
    @Test
    @Order(5)
    void testOrderCrud() {
        log.info("=== 开始测试订单CRUD操作 ===");

        org.tech.mongodemo.entity.Order order = org.tech.mongodemo.entity.Order.builder()
                .userId("user1")
                .orderNo("ORD-TEST-001")
                .items(Arrays.asList(
                        org.tech.mongodemo.entity.Order.OrderItem.builder()
                                .productId("p1")
                                .quantity(2)
                                .price(new BigDecimal("100.00"))
                                .build()
                ))
                .totalAmount(new BigDecimal("200.00"))
                .status("PENDING")
                .shippingAddress(org.tech.mongodemo.entity.Order.Address.builder()
                        .city("Shanghai")
                        .street("Test Road")
                        .zipCode("200000")
                        .build())
                .orderDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        org.tech.mongodemo.entity.Order savedOrder = orderRepository.save(order);
        log.info("创建订单成功: {}", savedOrder.getOrderNo());

        org.tech.mongodemo.entity.Order foundOrder = orderRepository.findById(savedOrder.getId()).orElse(null);
        assert foundOrder != null;
        assert foundOrder.getOrderNo().equals("ORD-TEST-001");
        log.info("查询订单成功: 状态 = {}", foundOrder.getStatus());

        foundOrder.setStatus("COMPLETED");
        org.tech.mongodemo.entity.Order updatedOrder = orderRepository.save(foundOrder);
        assert updatedOrder.getStatus().equals("COMPLETED");
        log.info("更新订单成功: 状态更新为 {}", updatedOrder.getStatus());

        orderRepository.deleteById(savedOrder.getId());
        assert orderRepository.findById(savedOrder.getId()).isEmpty();
        log.info("删除订单成功");

        log.info("=== 订单CRUD操作测试完成 ===");
    }

    /**
     * 测试订单聚合查询功能
     * 演示：聚合管道、用户订单统计、月度统计
     */
    @Test
    @Order(6)
    void testOrderAggregation() {
        log.info("=== 开始测试订单聚合查询 ===");

        List<OrderRepository.UserOrderStats> userStats = orderRepository.aggregateUserOrderStats();
        log.info("用户订单统计数量: {}", userStats.size());
        userStats.forEach(stats -> 
            log.info("用户 {}: 订单数={}, 总金额={}", 
                    stats.getUserId(), stats.getTotalOrders(), stats.getTotalAmount()));

        LocalDateTime startDate = LocalDateTime.now().minusMonths(6);
        List<OrderRepository.MonthlyStats> monthlyStats = orderRepository.aggregateMonthlyStats(startDate);
        log.info("月度统计数量: {}", monthlyStats.size());
        monthlyStats.forEach(stats ->
            log.info("月份 {}: 订单数={}, 总金额={}",
                    stats.getId(), stats.getOrderCount(), stats.getTotalAmount()));

        List<org.tech.mongodemo.entity.Order> highValueOrders = orderRepository.findOrdersWithAmountGreaterThan(new BigDecimal("5000"));
        log.info("高价值订单数量(>5000): {}", highValueOrders.size());

        List<org.tech.mongodemo.entity.Order> pendingOrders = orderRepository.findByStatus("PENDING");
        log.info("待处理订单数量: {}", pendingOrders.size());

        log.info("=== 订单聚合查询测试完成 ===");
    }

    /**
     * 测试应用上下文加载
     */
    @Test
    void contextLoads() {
        log.info("Spring Boot 应用上下文加载成功");
    }
}
