CREATE TABLE cart (
    cart_id BIGINT AUTO_INCREMENT,
    cart_type VARCHAR(20),
    cart_session_id VARCHAR(200),
    CONSTRAINT pk_cart_item_id PRIMARY KEY (cart_id)
);



CREATE TABLE cart_item (
                           cart_item_id INT AUTO_INCREMENT,
                           cart_item_type VARCHAR(20),
                           cart_id BIGINT NOT NULL,
                           book_id INT,
                           item_count INT,
                           price DECIMAL(5,2),
                           CONSTRAINT pk_cart_item_id PRIMARY KEY (cart_item_id),
                           CONSTRAINT fk_cart_item_book FOREIGN KEY (book_id) REFERENCES book (book_id),
                           CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES cart (cart_id)
);