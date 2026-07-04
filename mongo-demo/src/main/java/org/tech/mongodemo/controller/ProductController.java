
package org.tech.mongodemo.controller;

import org.tech.mongodemo.entity.Product;
import org.tech.mongodemo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品控制器 - 演示MongoDB查询和分页的REST API
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getProductsByCategory(category, pageable);
        return ResponseEntity.ok(productPage.getContent());
    }

    @GetMapping("/price/range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{category}/price/less/{maxPrice}")
    public ResponseEntity<List<Product>> getProductsByCategoryAndPrice(
            @PathVariable String category,
            @PathVariable BigDecimal maxPrice) {
        List<Product> products = productService.getProductsByCategoryAndPrice(category, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Product>> getProductsByTags(@RequestParam List<String> tags) {
        List<Product> products = productService.getProductsByTags(tags);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{category}/sorted")
    public ResponseEntity<List<Product>> getProductsSortedByPrice(@PathVariable String category) {
        List<Product> products = productService.getProductsSortedByPrice(category);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/stock/decrease")
    public ResponseEntity<Integer> decreaseStock(@PathVariable String id) {
        int updated = productService.decreaseStock(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Integer> updateStock(
            @PathVariable String id,
            @RequestParam Integer stock) {
        int updated = productService.updateStock(id, stock);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/count/category/{category}")
    public ResponseEntity<Long> countProductsByCategory(@PathVariable String category) {
        long count = productService.countProductsByCategory(category);
        return ResponseEntity.ok(count);
    }
}
