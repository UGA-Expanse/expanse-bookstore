CREATE TABLE `stock` (
         `stock_id` char(36) NOT NULL,
         `book_id` int NOT NULL,
         `publisher_id` int NOT NULL,
         `quantity` int NOT NULL DEFAULT '0',
         `price` double(16,2) DEFAULT '0.00',
         PRIMARY KEY (`stock_id`),
         KEY `fk_stock_book` (`book_id`),
         KEY `fk_stock_publisher` (`publisher_id`),
         CONSTRAINT `fk_stock_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
         CONSTRAINT `fk_stock_publisher` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`publisher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
