CREATE TABLE IF NOT EXISTS `user_album_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`user_id` int(11) NOT NULL,
	`album_id` int(11) DEFAULT NULL,
    `step` int(11) DEFAULT NULL,
    `complete` int(11) DEFAULT NULL,
    `priview_img` varchar(255) DEFAULT NULL,
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 init',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序用户相册信息表';

CREATE TABLE IF NOT EXISTS `user_album_item_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`user_album_id` int(11) NOT NULL,
	`album_id` int(11) NOT NULL,
	`album_item_id` int(11) NOT NULL,
	`user_origin_img_url` varchar(255) DEFAULT NULL,
    `preview_img_url` varchar(255) DEFAULT NULL,
    `rank` int(11) NOT NULL,
	`edit_img_infos` varchar(255) DEFAULT NULL,
    `edit_text_infos` varchar(255) DEFAULT NULL,
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 init',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序用户相册子信息表';

CREATE TABLE IF NOT EXISTS `album_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`title` varchar(255) NOT NULL,
	`keyword` varchar(255) NOT NULL,
	`total_items` int(11) NOT NULL,
	`cover` varchar(255)  NOT NULL,
	`priview_img` varchar(255)  NOT NULL,
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 init',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序相册信息表';

CREATE TABLE IF NOT EXISTS `album_item_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`album_id` int(11) NOT NULL,
	`edit_img_url` varchar(255) NOT NULL,
	`preview_img_url` varchar(255) NOT NULL,
	`rank` int(11) NOT NULL,
	`edit_img_infos` varchar(255) NOT NULL,
	`edit_text_infos` varchar(255) NOT NULL,
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 init',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序相册子信息表';