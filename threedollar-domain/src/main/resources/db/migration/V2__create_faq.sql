CREATE TABLE `faq`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `category`   VARCHAR(30)  NOT NULL,
    `question`   VARCHAR(100) NOT NULL,
    `answer`     VARCHAR(200) NOT NULL,
    `created_at` DATETIME(6) DEFAULT NULL,
    `updated_at` DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;
