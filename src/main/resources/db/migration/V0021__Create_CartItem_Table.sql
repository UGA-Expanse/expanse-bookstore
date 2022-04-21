CREATE TABLE cart_item (
                           cart_item_id INT AUTO_INCREMENT,
                           cart_item_type VARCHAR(20),
                           book_id INT,
                           item_count INT,
                           price DECIMAL(5,2),
                           CONSTRAINT pk_cart_item_id PRIMARY KEY (cart_item_id),
                           CONSTRAINT fk_cart_item_book FOREIGN KEY (book_id) REFERENCES book (book_id)
);