CREATE TABLE IF NOT EXISTS `album_cover_item_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`album_id`  int(11) NOT NULL DEFAULT '0' COMMENT 'reflect album id',
	`cover_album_item_id` int(11) NOT NULL DEFAULT '0' COMMENT 'share the album item〜s edit info',
	`edit_img_url` varchar(255) DEFAULT NULL,
	`preview_img_url` varchar(255) DEFAULT NULL,
	`shadow_img_url` varchar(255) DEFAULT NULL,
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 init',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY UK_AII_AI_R(`album_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序相册封面可编辑信息表';

ALTER TABLE user_album_info ADD COLUMN `cover_img` VARCHAR(255) DEFAULT '' AFTER `preview_img`;