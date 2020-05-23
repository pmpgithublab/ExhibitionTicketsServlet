CREATE TABLE `payments`
(
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `tickets_quantity` int    DEFAULT NULL,
    `paid_date`        date   DEFAULT NULL,
    `paid_sum`         bigint DEFAULT NULL,
    `user_id`          bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKUser_idx` (`user_id`),
    CONSTRAINT `FKUser` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
