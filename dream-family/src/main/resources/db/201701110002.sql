use family_developer;
CREATE TABLE IF NOT EXISTS `activity_info`(
	`id` int unsigned not null auto_increment,
	`title` blob,
	`introduction` blob,
	`content` blob,
	`content_css` blob,
	`cover` VARCHAR(255) DEFAULT '',
	`start_date` datetime,
	`end_date` datetime,
	`finish` int default 0 COMMENT '0 INIT 1 FINISHED',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '活动信息';

CREATE TABLE IF NOT EXISTS `activity_vote_stat_info`(
	`id` int unsigned not null COMMENT 'reference activity_works id',
	`activity_id` int unsigned not null comment 'reference activity_info.id',
	`votes` int unsigned not null default 0,
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    KEY (`activity_id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '投票信息统计表';


CREATE TABLE IF NOT EXISTS `activity_vote_detail_info`(
	`id` int unsigned not null auto_increment,
	`work_id` int unsigned not null comment 'reference activity_works_info.id',
	`user_id` int unsigned not null comment 'reference user_info.id',
	`vote_date` date not null,
	`ip` varchar(128) not null,
	`vote_time` datetime not null,
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`work_id`, `user_id`, `vote_date`),
    KEY (`user_id`),
    KEY (`vote_date`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '投票信息详情表';

CREATE TABLE IF NOT EXISTS `activity_works_info`(
	`id` int unsigned not null auto_increment,
	`activity_id` int unsigned not null comment 'reference activity_info.id',
	`user_id` int unsigned not null comment 'reference user_info.id',
	`type` int unsigned not null comment '作品类型 0：非视频 1:视频 2:音频',
	`solgan` blob,
	`desc` blob,
	`cover` VARCHAR(255) DEFAULT '',
	`resource_url` VARCHAR(255) DEFAULT '',
	`durations` int default 0,
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`user_id`, `activity_id`),
    KEY (`activity_id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '投票作品详情表';

