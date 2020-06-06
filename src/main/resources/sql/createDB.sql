CREATE DATABASE `servletproject`;
USE servletproject;


CREATE TABLE `users`
(
    `id`       bigint       NOT NULL AUTO_INCREMENT,
    `email`    varchar(255) NOT NULL,
    `name_en`  varchar(45)  NOT NULL,
    `name_uk`  varchar(45)  NOT NULL,
    `password` varchar(64)  NOT NULL,
    `role`     varchar(20)  NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 96
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `halls`
(
    `id`      bigint       NOT NULL AUTO_INCREMENT,
    `name_en` varchar(200) NOT NULL,
    `name_uk` varchar(200) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name_en_UNIQUE` (`name_en`),
    UNIQUE KEY `name_uk_UNIQUE` (`name_uk`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `exhibits`
(
    `id`                   bigint       NOT NULL AUTO_INCREMENT,
    `name_en`              varchar(200) NOT NULL,
    `name_uk`              varchar(200) NOT NULL,
    `start_date_time`      datetime     NOT NULL,
    `end_date_time`        datetime     NOT NULL,
    `ticket_cost`          bigint       NOT NULL,
    `max_visitors_per_day` int          NOT NULL,
    `hall_id`              bigint       NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name_en_UNIQUE` (`name_en`),
    UNIQUE KEY `name_uk_UNIQUE` (`name_uk`),
    KEY `FKHall_idx` (`hall_id`),
    CONSTRAINT `FKHall` FOREIGN KEY (`hall_id`) REFERENCES `halls` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


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
