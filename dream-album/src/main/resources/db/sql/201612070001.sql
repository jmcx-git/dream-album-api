CREATE TABLE IF NOT EXISTS `user_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`nick_name` varchar(200) DEFAULT NULL,
	`gender` int(2) DEFAULT NULL,
    `city` varchar(12) DEFAULT NULL,
    `province` varchar(12) DEFAULT NULL,
    `country` varchar(12) DEFAULT NULL,
    `avatar_url` varchar(255) DEFAULT NULL,
    `open_id` varchar(128) DEFAULT NULL,
    `union_id` varchar(128) DEFAULT NULL,
    `appid` varchar(64) DEFAULT NULL,
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 init',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序用户信息表';