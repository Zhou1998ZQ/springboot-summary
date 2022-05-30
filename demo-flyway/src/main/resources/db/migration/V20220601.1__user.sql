DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                        `user_id` bigint(20) unsigned NOT NULL,
                        `user_name` varchar(32) NOT NULL,
                        `user_role_id` bigint(20) NOT NULL,
                        PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
