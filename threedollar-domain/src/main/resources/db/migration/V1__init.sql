CREATE TABLE `user`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `social_id`   VARCHAR(200) NOT NULL,
    `social_type` VARCHAR(30)  NOT NULL,
    `name`        VARCHAR(50) DEFAULT NULL,
    `status`      VARCHAR(30)  NOT NULL,
    `created_at`  DATETIME(6) DEFAULT NULL,
    `updated_at`  DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_user_1` (`social_id`, `social_type`),
    KEY `idx_user_1` (`name`)
) ENGINE = InnoDB;


CREATE TABLE `withdrawal_user`
(
    `user_id`         BIGINT       NOT NULL,
    `name`            VARCHAR(50) DEFAULT NULL,
    `social_id`       VARCHAR(200) NOT NULL,
    `social_type`     VARCHAR(30)  NOT NULL,
    `user_created_at` DATETIME(6) DEFAULT NULL,
    `created_at`      DATETIME(6) DEFAULT NULL,
    `updated_at`      DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB;


CREATE TABLE `store`
(
    `id`         BIGINT           NOT NULL AUTO_INCREMENT,
    `user_id`    BIGINT           NOT NULL,
    `latitude`   DOUBLE PRECISION NOT NULL,
    `longitude`  DOUBLE PRECISION NOT NULL,
    `store_name` VARCHAR(300)     NOT NULL,
    `store_type` VARCHAR(30)               DEFAULT NULL,
    `rating`     DOUBLE PRECISION          DEFAULT 0,
    `status`     VARCHAR(30)      NOT NULL DEFAULT 'ACTIVE',
    `created_at` DATETIME(6)               DEFAULT NULL,
    `updated_at` DATETIME(6)               DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;


CREATE TABLE `store_image`
(
    `id`         BIGINT     NOT NULL AUTO_INCREMENT,
    `store_id`   BIGINT     NOT NULL,
    `user_id`    BIGINT              DEFAULT NULL,
    `url`        VARCHAR(255)        DEFAULT NULL,
    `is_deleted` TINYINT(1) NOT NULL DEFAULT FALSE,
    `created_at` DATETIME(6)         DEFAULT NULL,
    `updated_at` DATETIME(6)         DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_store_image_1` (`store_id`)
) ENGINE = InnoDB;


CREATE TABLE `review`
(
    `id`         BIGINT  NOT NULL AUTO_INCREMENT,
    `store_id`   BIGINT  NOT NULL,
    `user_id`    BIGINT  NOT NULL,
    `contents`   VARCHAR(300) DEFAULT NULL,
    `rating`     INTEGER NOT NULL,
    `status`     VARCHAR(255) DEFAULT NULL,
    `created_at` DATETIME(6)  DEFAULT NULL,
    `updated_at` DATETIME(6)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_review_1` (`store_id`),
    KEY `idx_review_2` (`user_id`)
) ENGINE = InnoDB;


CREATE TABLE `appearance_day`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `store_id`   BIGINT      NOT NULL,
    `day`        VARCHAR(30) NOT NULL,
    `created_at` DATETIME(6) DEFAULT NULL,
    `updated_at` DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_appearance_day_1` (`store_id`)
) ENGINE = InnoDB;

CREATE TABLE `menu`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `store_id`   BIGINT      NOT NULL,
    `name`       VARCHAR(50)  DEFAULT NULL,
    `price`      VARCHAR(100) DEFAULT NULL,
    `category`   VARCHAR(30) NOT NULL,
    `created_at` DATETIME(6)  DEFAULT NULL,
    `updated_at` DATETIME(6)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_menu_1` (`store_id`)
) engine = InnoDB;

CREATE TABLE `payment_method`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `store_id`   BIGINT      NOT NULL,
    `method`     VARCHAR(30) NOT NULL,
    `created_at` DATETIME(6) DEFAULT NULL,
    `updated_at` DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_payment_method_1` (`store_id`)
) ENGINE = InnoDB;


CREATE TABLE `store_delete_request`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `reason`     VARCHAR(30) NOT NULL,
    `store_id`   BIGINT      NOT NULL,
    `user_id`    BIGINT      NOT NULL,
    `created_at` DATETIME(6) DEFAULT NULL,
    `updated_at` DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY `idx_store_delete_request_1` (`store_id`)
) ENGINE = InnoDB;

