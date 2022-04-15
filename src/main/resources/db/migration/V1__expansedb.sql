create table if not exists USER
(
    `id` BINARY(16) NOT NULL UNIQUE,
    `username` varchar(19) not null unique,
    `email` varchar(50) not null unique,
    `password` varchar(20) not null,
    `isadmin` BIT default (0)
);

CREATE INDEX `userindex`
    ON `USER` (`username`);
