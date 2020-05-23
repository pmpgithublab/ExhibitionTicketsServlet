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
