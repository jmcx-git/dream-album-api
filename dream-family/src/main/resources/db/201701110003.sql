use family_developer;
CREATE TABLE IF NOT EXISTS `activity_prize_info`(
	`id` int unsigned not null auto_increment,
	`activity_id` int unsigned not null COMMENT 'reference activity_info.id',
	`prize_id` int unsigned not null COMMENT 'reference prize_info.id',
	`rank` int unsigned not null COMMENT '越小奖越大',
	`rank_desc`  VARCHAR(255) not null COMMENT '一等奖，二等奖...',
	`count` int unsigned not null COMMENT '',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY(`activity_id`, `rank`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '活动奖品信息';

CREATE TABLE IF NOT EXISTS `activity_user_prize_info`(
	`id` int unsigned not null auto_increment,
	`activity_id` int unsigned not null COMMENT 'reference activity_info.id',
	`works_id` int unsigned not null COMMENT 'reference activity_works_info.id',
	`activity_prize_id` int unsigned not null COMMENT 'reference activity_prize_info.id',
	`prize_id` int unsigned not null COMMENT 'reference prize_info.id',
	`user_id` int unsigned not null COMMENT 'reference user_info.id',
	`build` int(11) NOT NULL DEFAULT 0  COMMENT '0 init 1 build',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY(`activity_id`, `works_id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '活动用户中奖信息';

CREATE TABLE IF NOT EXISTS `prize_info`(
	`id` int unsigned not null auto_increment,
	`title`  VARCHAR(255) not null COMMENT '奖品名称',
	`desc`  VARCHAR(255) not null COMMENT '奖品描述',
	`img`  VARCHAR(255)  COMMENT '奖品图片地址',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '奖品信息';

CREATE TABLE IF NOT EXISTS `system_notice_info`(
	`id` int unsigned not null auto_increment,
	`desc`  VARCHAR(255) not null COMMENT '系统通知描述',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '系统通知';

CREATE TABLE IF NOT EXISTS `user_notice_info`(
	`id` int unsigned not null auto_increment,
	`user_id` int unsigned not null COMMENT 'user_info.id',
	`desc`  VARCHAR(255) not null COMMENT '描述',
	`read` int unsigned not null COMMENT '0 INIT 1 READ',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户通知';

CREATE TABLE IF NOT EXISTS `user_read_notice_record`(
	`id` int unsigned not null comment 'user_info.id',
	`read_time` datetime not null COMMENT '用户上次读取消息的时间',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户读取通知记录';

CREATE TABLE IF NOT EXISTS `activity_works_example_info`(
	`id` int unsigned not null comment 'user_info.id',
	`activity_id` int unsigned not null COMMENT 'reference activity_info.id',
	`title`  VARCHAR(255) not null COMMENT '标题',
	`img`  VARCHAR(255) not null COMMENT '图片',
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '参赛作品示例';

ALTER TABLE `activity_info` drop column `content_css`;
ALTER TABLE `activity_info` ADD COLUMN `activity_rule` text after `end_date`;




