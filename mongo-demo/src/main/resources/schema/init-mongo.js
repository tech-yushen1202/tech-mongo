
// MongoDB 数据库初始化脚本
// 使用方式：mongo < init-mongo.js

// 创建数据库
use demo_db;

// 创建用户集合 (users) - 演示文档结构
db.createCollection("users");

// 创建产品集合 (products) - 演示索引
db.createCollection("products");

// 创建订单集合 (orders) - 演示嵌入式文档
db.createCollection("orders");

// 创建用户索引 - 演示单字段索引
db.users.createIndex({ email: 1 }, { unique: true });
db.users.createIndex({ username: 1 });

// 创建产品索引 - 演示复合索引
db.products.createIndex({ category: 1, price: -1 });
db.products.createIndex({ name: "text", description: "text" });

// 创建订单索引
db.orders.createIndex({ userId: 1 });
db.orders.createIndex({ orderDate: -1 });

// 插入示例用户数据
db.users.insertMany([
    {
        username: "zhangsan",
        email: "zhangsan@example.com",
        password: "encrypted_password_1",
        age: 28,
        address: {
            city: "Beijing",
            street: "Main Street 1",
            zipCode: "100000"
        },
        roles: ["user", "admin"],
        createdAt: new Date()
    },
    {
        username: "lisi",
        email: "lisi@example.com",
        password: "encrypted_password_2",
        age: 35,
        address: {
            city: "Shanghai",
            street: "Second Street 2",
            zipCode: "200000"
        },
        roles: ["user"],
        createdAt: new Date()
    },
    {
        username: "wangwu",
        email: "wangwu@example.com",
        password: "encrypted_password_3",
        age: 22,
        address: {
            city: "Guangzhou",
            street: "Third Street 3",
            zipCode: "510000"
        },
        roles: ["user"],
        createdAt: new Date()
    }
]);

// 插入示例产品数据
db.products.insertMany([
    {
        name: "MacBook Pro",
        description: "Apple MacBook Pro 14-inch M3 Pro",
        price: 14999,
        category: "Electronics",
        stock: 100,
        tags: ["laptop", "apple", "pro"],
        createdAt: new Date()
    },
    {
        name: "iPhone 15 Pro",
        description: "Apple iPhone 15 Pro 256GB",
        price: 8999,
        category: "Electronics",
        stock: 200,
        tags: ["phone", "apple", "pro"],
        createdAt: new Date()
    },
    {
        name: "Sony Headphones",
        description: "Sony WH-1000XM5 Wireless Headphones",
        price: 2499,
        category: "Electronics",
        stock: 150,
        tags: ["headphones", "sony", "wireless"],
        createdAt: new Date()
    },
    {
        name: "Nike Air Max",
        description: "Nike Air De'Air Max 270",
        price: 1299,
        category: "Shoes",
        stock: 300,
        tags: ["shoes", "nike", "sport"],
        createdAt: new Date()
    },
    {
        name: "Adidas T-Shirt",
        description: "Adidas Originals T-Shirt",
        price: 399,
        category: "Clothing",
        stock: 500,
        tags: ["tshirt", "adidas", "casual"],
        createdAt: new Date()
    }
]);

// 插入示例订单数据
db.orders.insertMany([
    {
        userId: "user1",
        orderNo: "ORD-2024-0001",
        items: [
            { productId: "p1", quantity: 1, price: 14999 },
            { productId: "p3", quantity: 2, price: 2499 }
        ],
        totalAmount: 19997,
        status: "COMPLETED",
        shippingAddress: {
            city: "Beijing",
            street: "Main Street 1",
            zipCode: "100000"
        },
        orderDate: new Date("2024-01-15T10:30:00Z"),
        createdAt: new Date()
    },
    {
        userId: "user2",
        orderNo: "ORD-2024-0002",
        items: [
            { productId: "p2", quantity: 1, price: 8999 }
        ],
        totalAmount: 8999,
        status: "PENDING",
        shippingAddress: {
            city: "Shanghai",
            street: "Second Street 2",
            zipCode: "200000"
        },
        orderDate: new Date("2024-01-16T14:20:00Z"),
        createdAt: new Date()
    }
]);

print("MongoDB initialization completed successfully!");
print("Collections created: users, products, orders");
print("Indexes created for optimization");
print("Sample data inserted");
