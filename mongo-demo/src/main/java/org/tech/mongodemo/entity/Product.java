
package org.tech.mongodemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 产品实体类 - 演示MongoDB索引和文本搜索
 * 包含复合索引(category, price)和文本索引(name, description)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
@CompoundIndex(def = "{'category': 1, 'price': -1}", name = "category_price_idx")
public class Product {

    @Id
    private String id;

    @TextIndexed
    @Field("name")
    private String name;

    @TextIndexed
    @Field("description")
    private String description;

    @Field("price")
    private BigDecimal price;

    @Field("category")
    private String category;

    @Field("stock")
    private Integer stock;

    @Field("tags")
    private List<String> tags;

    @Field("createdAt")
    private LocalDateTime createdAt;
}
