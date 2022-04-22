CREATE TABLE category (
                                            `category_id` INT AUTO_INCREMENT NOT NULL,
                                            `name` VARCHAR(255) NOT NULL,
                                            `picture` VARCHAR(200),
                                             PRIMARY KEY (`category_id`),
                                             UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);


CREATE TABLE category_item (
                                id INT auto_increment,
                                book_id INT,
                                category_id INT,
                                CONSTRAINT pk_cat_item PRIMARY KEY (id),
                                CONSTRAINT fk_cat_catitem FOREIGN KEY (category_id) REFERENCES category (category_id),
                                CONSTRAINT fk_book_catitem FOREIGN KEY (book_id) REFERENCES book (book_id),
                                CONSTRAINT cat_item_unique UNIQUE (book_id, category_id)
);