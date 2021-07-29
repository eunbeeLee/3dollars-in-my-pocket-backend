ALTER TABLE `store`
    ADD INDEX `idx_store_3` (`id`, `latitude`, `longitude`);

ALTER TABLE `review`
    DROP index `idx_review_1`;

ALTER TABLE `review`
    DROP index `idx_review_2`;

ALTER TABLE `review`
    DROP index `idx_review_3`;

ALTER TABLE `review`
    ADD INDEX `idx_review_1` (`user_id`, `status`);

ALTER TABLE `review`
    ADD INDEX `idx_review_2` (`store_id`, `status`);
