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
