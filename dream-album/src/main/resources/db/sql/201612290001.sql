CREATE TABLE IF NOT EXISTS `album_item_edit_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`album_item_id` int(11) NOT NULL DEFAULT '0',
	`rank` int(11) NOT NULL DEFAULT '0',
	`edit_img_url` varchar(255) DEFAULT '', 
	`css_elm_move_x` int(11) DEFAULT '0',
	`css_elm_move_y` int(11) DEFAULT '0',
    `css_elm_width` int(11) DEFAULT '0',
    `css_elm_height` int(11) DEFAULT '0',
	`css_elm_rotate` int(11) DEFAULT '0',
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 init',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY UK_AIEI_AII_R(`album_item_id`,`rank`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序相册子模版可编辑信息表';

CREATE TABLE IF NOT EXISTS `user_album_item_edit_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`user_album_item_id` int(11) NOT NULL DEFAULT '0',
	`rank` int(11) NOT NULL DEFAULT '0',
	`user_origin_img_url` varchar(255) DEFAULT '', 
	`css_elm_move_x` int(11) DEFAULT '0',
	`css_elm_move_y` int(11) DEFAULT '0',
    `css_elm_width` int(11) DEFAULT '0',
    `css_elm_height` int(11) DEFAULT '0',
	`css_elm_rotate` int(11) DEFAULT '0',
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 init',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY UK_UAIEI_UAII_R(`user_album_item_id`,`rank`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序用户相册子信息编辑记录表';