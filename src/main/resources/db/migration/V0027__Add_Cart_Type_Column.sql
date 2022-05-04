ALTER TABLE cart
    ADD COLUMN cart_life enum('EPHEMERAL','PERSISTENT') DEFAULT 'EPHEMERAL';

INSERT INTO `user`
(`id`,
 `name`,
 `password`,
 `username`)
VALUES
    (7,
        'anony',
        '1234',
        'anonymousUser');


INSERT INTO`customer`
(`customer_id`,
 `first_name`,
 `last_name`,
 `email`,
 `userid`)
VALUES
    (10000,
     'DUDD',
     'DUD',
     'anonymoususer@j5.rocks',
     'anonymousUser');


