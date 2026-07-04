
package org.tech.mongodemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MongoDB Demo Application 启动类
 * 演示MongoDB基本功能，包括CRUD操作、查询、聚合等
 */
@SpringBootApplication
public class MongoDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoDemoApplication.class, args);
    }
}
