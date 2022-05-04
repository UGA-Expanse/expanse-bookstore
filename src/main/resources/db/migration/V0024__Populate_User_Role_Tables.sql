INSERT INTO `expanse_books`.`role` (`id`, `name`) VALUES (1,'ROLE_USER');
INSERT INTO `expanse_books`.`role` (`id`, `name`) VALUES (2,'ROLE_MERCHANT');
INSERT INTO `expanse_books`.`role` (`id`, `name`) VALUES (3,'ROLE_ADMIN');
INSERT INTO `expanse_books`.`role` (`id`, `name`) VALUES (4,'ROLE_SUPER_ADMIN');
INSERT INTO `expanse_books`.`role` (`id`, `name`) VALUES (5,'ROLE_VENDOR');

INSERT INTO `expanse_books`.`user` (`id`, `name`, `password`, `username`) VALUES (1, 'John Travolta', '1234', 'john');
INSERT INTO `expanse_books`.`user` (`id`, `name`, `password`, `username`) VALUES (2, "Song Joo A", '1234', 'jooa');
INSERT INTO `expanse_books`.`user` (`id`, `name`, `password`, `username`) VALUES (3, 'Jim Carrey', '1234', 'jim');
INSERT INTO `expanse_books`.`user` (`id`, `name`, `password`, `username`) VALUES (4, 'Denzel Washington', '1234', 'denzel');
INSERT INTO `expanse_books`.`user` (`id`, `name`, `password`, `username`) VALUES (5, 'Lewis Hamilton', '1234', 'lewis');
INSERT INTO `expanse_books`.`user` (`id`, `name`, `password`, `username`) VALUES (6, 'George Russell', '1234', 'george');

INSERT INTO `expanse_books`.`user_roles` (`user_id`, `roles_id`) VALUES (1,1);
INSERT INTO `expanse_books`.`user_roles` (`user_id`, `roles_id`) VALUES ( 4,2);
INSERT INTO `expanse_books`.`user_roles` (`user_id`, `roles_id`) VALUES ( 3,5);
INSERT INTO `expanse_books`.`user_roles` (`user_id`, `roles_id`) VALUES (5,2);
INSERT INTO `expanse_books`.`user_roles` (`user_id`, `roles_id`) VALUES (5,3);
INSERT INTO `expanse_books`.`user_roles` (`user_id`, `roles_id`) VALUES (6,4);
INSERT INTO `expanse_books`.`user_roles` (`user_id`, `roles_id`) VALUES (2,1);