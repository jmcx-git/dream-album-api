use family_developer;
CREATE TABLE IF NOT EXISTS `wiki_info`(
	`id` int unsigned not null auto_increment,
	`title` blob,
	`content` blob,
	`status` int(11) NOT NULL DEFAULT '0',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_time` datetime NOT NULL COMMENT '记录创建时间',
    PRIMARY KEY (`id`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT '如何玩转光阴之旅';

update feed_info set illustration = concat('{"index":0,"url":"', `illustration`);
update feed_info set illustration = concat(`illustration`, '"}');
update activity_works_info set illustration = concat('{"index":0,"url":"', `cover`);
update activity_works_info set illustration = concat(`illustration`, '"}');

ALTER TABLE `activity_works_info` add column `illustration` text after `cover`;
ALTER TABLE `user_notice_info` add column `img_url` varchar(255) after `desc`;
