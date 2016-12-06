CREATE TABLE IF NOT EXISTS `wechat_app_album_user_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`user_id` varchar(255) NOT NULL,
	`open_id` varchar(255) DEFAULT NULL,
    `nickname` varchar(255) DEFAULT NULL,
    `gender` varchar(255) DEFAULT NULL,
    `city` varchar(255) DEFAULT NULL,
    `province` varchar(255) DEFAULT NULL,
    `country` varchar(255) DEFAULT NULL,
    `avatar_url` varchar(255) DEFAULT NULL,
    `union_id` varchar(255) DEFAULT NULL,
    `created_album` varchar(255) DEFAULT NULL,
    `handler_album` varchar(255) DEFAULT NULL,
    `collect_album` varchar(255) DEFAULT NULL,
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 init',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_WAAUI_UI`(`user_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序用户信息表';

CREATE TABLE IF NOT EXISTS `wechat_app_album_created_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`user_id` varchar(255) NOT NULL,
	`template_id` int(11) NOT NULL,
	`template_handle_id` int(11) NOT NULL,
	`h5_url` varchar(255) DEFAULT NULL,
    `png_url` varchar(255) DEFAULT NULL,
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 init',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序相册已创建表';

CREATE TABLE IF NOT EXISTS `wechat_app_album_handle_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`user_id` varchar(255) NOT NULL,
	`template_id` int(11) NOT NULL,
	`template_handle_id` int(11) NOT NULL,
	`step` int(11) NOT NULL,
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 init',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_WAAHI_UI_TI`(`user_id`,`template_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序相册草稿信息表';

CREATE TABLE IF NOT EXISTS `wechat_app_album_template_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`background_img` varchar(255) NOT NULL,
	`template_info` varchar(255) NOT NULL,
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 init',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序相册模版信息表';

CREATE TABLE IF NOT EXISTS `wechat_app_album_template_handle_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`handle_info` varchar(255) NOT NULL,
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 init',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序相册模版操作信息表';