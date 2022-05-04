# RENAME TABLE USER TO USERO;

create table if not exists USERO
(
    `username` varchar(19) not null unique,
    `email` varchar(50) not null unique,
    `password` varchar(20) not null,
    `isadmin` BIT default (0)
);

CREATE INDEX `userindex`
    ON `USERO` (`username`);