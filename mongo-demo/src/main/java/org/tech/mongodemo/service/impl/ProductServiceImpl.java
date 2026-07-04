
package org.tech.mongodemo.service.impl;

import org.tech.mongodemo.entity.Product;
import org.tech.mongodemo.repository.ProductRepository;
import org.tech.mongodemo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 产品服务实现类 - 演示MongoDB查询和分页实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        log.info("Creating product: {}", product.getName());
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(String id) {
        log.info("Getting product by id: {}", id);
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Getting all products");
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getProductsByCategory(String category, Pageable pageable) {
        log.info("Getting products by category: {}", category);
        return productRepository.findByCategory(category, pageable);
    }

    @Override
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        log.info("Getting products with price between {} and {}", minPrice, maxPrice);
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Product> getProductsByCategoryAndPrice(String category, BigDecimal maxPrice) {
        log.info("Getting products in category {} with price less than {}", category, maxPrice);
        return productRepository.findByCategoryAndPriceLessThan(category, maxPrice);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        log.info("Searching products with keyword: {}", keyword);
        return productRepository.searchByText(keyword);
    }

    @Override
    public List<Product> getProductsByTags(List<String> tags) {
        log.info("Getting products with tags: {}", tags);
        return productRepository.findByTagsContaining(tags);
    }

    @Override
    public List<Product> getProductsSortedByPrice(String category) {
        log.info("Getting products in category {} sorted by price desc", category);
        return productRepository.findByCategoryOrderByPriceDesc(category);
    }

    @Override
    public Product updateProduct(String id, Product product) {
        log.info("Updating product with id: {}", id);
        return productRepository.findById(id)
                .map(existingProduct -> {
                    if (product.getName() != null) {
                        existingProduct.setName(product.getName());
                    }
                    if (product.getDescription() != null) {
                        existingProduct.setDescription(product.getDescription());
                    }
                    if (product.getPrice() != null) {
                        existingProduct.setPrice(product.getPrice());
                    }
                    if (product.getCategory() != null) {
                        existingProduct.setCategory(product.getCategory());
                    }
                    if (product.getStock() != null) {
                        existingProduct.setStock(product.getStock());
                    }
                    if (product.getTags() != null) {
                        existingProduct.setTags(product.getTags());
                    }
                    return productRepository.save(existingProduct);
                })
                .orElse(null);
    }

    @Override
    public void deleteProduct(String id) {
        log.info("Deleting product with id: {}", id);
        productRepository.deleteById(id);
    }

    @Override
    public int decreaseStock(String productId) {
        log.info("Decreasing stock for product: {}", productId);
        return productRepository.decreaseStock(productId);
    }

    @Override
    public int updateStock(String productId, Integer stock) {
        log.info("Updating stock for product {} to {}", productId, stock);
        return productRepository.updateStock(productId, stock);
    }

    @Override
    public long countProductsByCategory(String category) {
        log.info("Counting products in category: {}", category);
        return productRepository.countByCategory(category);
    }
}
