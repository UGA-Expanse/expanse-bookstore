CREATE TABLE category_item (
                               id INT auto_increment,
                               book_id INT,
                               category_id INT,
                               CONSTRAINT pk_cat_item PRIMARY KEY (id),
                               CONSTRAINT fk_cat_catitem FOREIGN KEY (category_id) REFERENCES category (id),
                               CONSTRAINT fk_book_catitem FOREIGN KEY (book_id) REFERENCES book (book_id)
);