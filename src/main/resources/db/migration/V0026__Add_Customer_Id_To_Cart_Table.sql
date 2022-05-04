ALTER TABLE cart
    ADD COLUMN customer_id INT,
    ADD COLUMN active BOOLEAN,
    ADD CONSTRAINT fk_cart_custmr FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`);

ALTER TABLE user ADD INDEX user_idx (`username`) ;

ALTER TABLE customer
    ADD COLUMN userid varchar(255),
    ADD CONSTRAINT fk_customer_login FOREIGN KEY (`userid`) REFERENCES `user` (`username`);

