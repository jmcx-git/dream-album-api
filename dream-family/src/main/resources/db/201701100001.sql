USE family;
CREATE TABLE IF NOT EXISTS `user_info`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`nick_name` blob DEFAULT NULL,
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
    PRIMARY KEY (`id`),
     UNIQUE KEY `UK_OPENID`(`open_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序用户信息表';

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

CREATE TABLE IF NOT EXISTS `space_info`(
	`id` int unsigned not null auto_increment,
	`user_id` int unsigned not null comment 'reference user_info.id',
	`title` blob,
	`darling_name` blob,
	`darling_born_date` datetime,
	`darling_type` int unsigned not null default 0 comment '0:baby,1:love reference SpaceTypeEnum',
	`icon` varchar(255) DEFAULT '',	
	`cover` VARCHAR(255) DEFAULT '',	
	`info` blob,
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    KEY `KEY_SI_USERID`(`user_id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户空间表';

CREATE TABLE IF NOT EXISTS `space_secert_info`(
	`id` int unsigned not null auto_increment,
	`space_id` int unsigned not null comment 'reference space_info.id',
	`secert` varchar(32),
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY(`space_id`),
    KEY(`space_id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '空间密码表';

CREATE TABLE IF NOT EXISTS `user_space_relationship_info`(
	`id` int unsigned not null auto_increment,
	`space_id` int unsigned not null comment 'reference space_info.id',
	`user_id` int unsigned not null comment 'reference user_info.id',
	`relationship` int(11) NOT NULL DEFAULT '0' COMMENT 'REFERENCE SpaceRelationshipEnum 1 owner 0 occupant',
	`top` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '1 MEANS IN THE SPACE LIST TOP EXCLUDE OWNER SPACE',
	`view` int(11) unsigned NOT NULL  DEFAULT '1',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY(`space_id`, `user_id`),
    KEY(`user_id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户与空间关系表';

CREATE TABLE IF NOT EXISTS `space_like_info`(
	`id` int unsigned not null auto_increment,
	`space_id` int unsigned not null comment 'reference space_info.id',
	`user_id` int unsigned not null comment 'reference user_info.id',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY(`space_id`, `user_id`),
    KEY `KEY_SLI_USERID`(`user_id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '空间点赞表';

CREATE TABLE IF NOT EXISTS `space_stat_info`(
	`id` int unsigned not null comment 'reference space_info.id',
	`views` int unsigned not null comment 'when invoke space feed list incr',
	`occupants` int unsigned not null comment 'reference user_space_relationship_info',
	`records` int unsigned not null comment 'reference feed_info',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '空间统计表';

CREATE TABLE IF NOT EXISTS `feed_info`(
	`id` int unsigned not null auto_increment,
	`user_id` int unsigned not null comment 'reference user_info.id',
	`space_id` int unsigned not null comment 'reference space_info.id',	
	`title` blob,
	`content` blob,
	`cover` VARCHAR(255) COMMENT '封面图',
	`illustration` VARCHAR(255) DEFAULT '' COMMENT '插图',
	`resource_url` varchar(255) DEFAULT '' COMMENT '音频，视频资源',	
	`duration` INT DEFAULT 0 COMMENT '音频，视频资源时长',
	`type` int unsigned not null default 0 comment '0:photo,1:diary 2:audio 3:video reference FeedTypeEnum',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    KEY `KEY_FI_USERID`(`user_id`),
    KEY `KEY_FI_SPACEID`(`space_id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT 'feed表';

CREATE TABLE IF NOT EXISTS `feed_comment_info`(
	`id` int unsigned not null auto_increment,
	`user_id` int unsigned not null comment 'reference user_info.id',
	`feed_id` int unsigned not null comment 'reference feed_info.id',
	`comment_ref_id` int DEFAULT NULL comment 'reference feed_comment_info.id if not null',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    KEY `KEY_FCI_USERID`(`user_id`),
    KEY `KEY_FCI_FEEDID`(`feed_id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT 'feed评论表';


CREATE TABLE IF NOT EXISTS `feed_like_info`(
	`id` int unsigned not null auto_increment,
	`user_id` int unsigned not null comment 'reference user_info.id',
	`feed_id` int unsigned not null comment 'reference feed_info.id',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    KEY `KEY_FLI_USERID`(`user_id`),
    UNIQUE KEY(`feed_id`, `user_id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT 'feed点赞表';


CREATE TABLE IF NOT EXISTS `feed_stat_info`(
	`id` int unsigned not null comment 'feed_info.id',
	`views` int unsigned not null comment 'when invoke space feed detail incr',
	`comments` int unsigned not null comment 'reference feed_comment_info',
	`likes` int unsigned not null comment 'reference feed_like_info',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT 'feed统计表';



INSERT IGNORE INTO `smallapp_developer_info` values(null, 'wxdfbe82261831dda7', 'eabaf170cf7c3993d3fbf02eadae42ac', '萌娃相册', 0, now(), now());
INSERT IGNORE INTO `smallapp_developer_info` values(null, 'wx0ddc8673b8df3827', '16619bf042c6ef47965f45af7e09afe8', '水滴相册', 0, now(), now());