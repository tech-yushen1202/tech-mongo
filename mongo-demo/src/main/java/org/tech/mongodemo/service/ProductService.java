
package org.tech.mongodemo.service;

import org.tech.mongodemo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品服务接口 - 演示MongoDB查询和分页
 */
public interface ProductService {

    Product createProduct(Product product);

    Product getProductById(String id);

    List<Product> getAllProducts();

    Page<Product> getProductsByCategory(String category, Pageable pageable);

    List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> getProductsByCategoryAndPrice(String category, BigDecimal maxPrice);

    List<Product> searchProducts(String keyword);

    List<Product> getProductsByTags(List<String> tags);

    List<Product> getProductsSortedByPrice(String category);

    Product updateProduct(String id, Product product);

    void deleteProduct(String id);

    int decreaseStock(String productId);

    int updateStock(String productId, Integer stock);

    long countProductsByCategory(String category);
}
