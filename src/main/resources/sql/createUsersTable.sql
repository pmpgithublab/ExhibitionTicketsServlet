CREATE TABLE `exhibits`
(
    `id`                   bigint       NOT NULL AUTO_INCREMENT,
    `ticket_cost`          bigint       NOT NULL,
    `start_date_time`      datetime     NOT NULL,
    `end_date_time`        datetime     NOT NULL,
    `hall_id`              bigint       NOT NULL,
    `max_visitors_per_day` int          NOT NULL,
    `name_en`              varchar(200) NOT NULL,
    `name_uk`              varchar(200) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name_en_UNIQUE` (`name_en`),
    UNIQUE KEY `name_uk_UNIQUE` (`name_uk`),
    KEY `FKHall_idx` (`hall_id`),
    CONSTRAINT `FKHall` FOREIGN KEY (`hall_id`) REFERENCES `halls` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
