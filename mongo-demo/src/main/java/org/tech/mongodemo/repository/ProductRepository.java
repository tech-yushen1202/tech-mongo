
package org.tech.mongodemo.repository;

import org.tech.mongodemo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品数据访问层 - 演示MongoDB查询和分页
 * 包含分页查询、范围查询、文本搜索等
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByCategory(String category);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findByCategoryAndPriceLessThan(String category, BigDecimal price);

    Page<Product> findByCategory(String category, Pageable pageable);

    @Query("{ 'price': { $gte: ?0 } }")
    List<Product> findProductsWithPriceGreaterThan(BigDecimal price);

    @Query("{ 'tags': { $in: ?0 } }")
    List<Product> findByTagsContaining(List<String> tags);

    @Query(value = "{ 'category': ?0 }", sort = "{ 'price': -1 }")
    List<Product> findByCategoryOrderByPriceDesc(String category);

    @Query("{ '$text': { '$search': ?0 } }")
    List<Product> searchByText(String keyword);

    @Query(value = "{ '_id': ?0 }")
    @Update("{ '$inc': { 'stock': -1 } }")
    int decreaseStock(String productId);

    @Query(value = "{ '_id': ?0 }")
    @Update("{ '$set': { 'stock': ?1 } }")
    int updateStock(String productId, Integer stock);

    long countByCategory(String category);
}
