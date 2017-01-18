CREATE TABLE IF NOT EXISTS `smallapp_developer_info`(
	`id` int unsigned not null auto_increment,
	`app_id` varchar(32) not null,
	`secret` varchar(64) not null,
	`name` varchar(64) not null,
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
     UNIQUE KEY `UK_SD_APPID`(`app_id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '小程序开发者信息表';

INSERT INTO `smallapp_developer_info` values(null, 'wxdfbe82261831dda7', 'eabaf170cf7c3993d3fbf02eadae42ac', '萌娃相册', 0, now(), now());
INSERT INTO `smallapp_developer_info` values(null, 'wx0ddc8673b8df3827', '16619bf042c6ef47965f45af7e09afe8', '水滴相册', 0, now(), now());
INSERT INTO `smallapp_developer_info` values(null, 'wx1c1943ad2a3edaf1', '02d54d767ba1ed06501fdecb02e7f160', '光阴之旅', 0, now(), now());