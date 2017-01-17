user family;
CREATE TABLE IF NOT EXISTS `user_space_interaction_info`(
	`id` int unsigned not null AUTO_INCREMENT,
	`user_id` int unsigned not null comment 'reference user_info.id',
	`space_id` int unsigned not null comment 'reference space_info.id',
	`views` int unsigned not null comment 'when invoke space feed list incr',
	`likes` int unsigned not null,
	`comments` int unsigned not null,
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY(`user_id`, `space_id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '空间与用户互动信息统计表';