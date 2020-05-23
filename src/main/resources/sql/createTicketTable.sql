CREATE TABLE `tickets`
(
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `exhibit_date`     date   NOT NULL,
    `tickets_quantity` int    NOT NULL,
    `tickets_sum`      bigint NOT NULL,
    `payment_id`       bigint DEFAULT NULL,
    `exhibit_id`       bigint NOT NULL,
    `hall_id`          bigint NOT NULL,
    `user_id`          bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKpaymemtId` (`payment_id`),
    KEY `FKexhibitId` (`exhibit_id`),
    KEY `FKHall_id_idx` (`hall_id`),
    KEY `FKUser_id_idx` (`user_id`),
    CONSTRAINT `FKexhibitId` FOREIGN KEY (`exhibit_id`) REFERENCES `exhibits` (`id`),
    CONSTRAINT `FKHallId` FOREIGN KEY (`hall_id`) REFERENCES `halls` (`id`),
    CONSTRAINT `FKpaymemtId` FOREIGN KEY (`payment_id`) REFERENCES `payments` (`id`),
    CONSTRAINT `FKUserId` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
