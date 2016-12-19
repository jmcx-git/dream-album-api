 alter table album_item_info add column `shadow_img_url` varchar(255) not null default '' after `preview_img_url`;
 
 ALTER TABLE album_info change `priview_img` `preview_img` varchar(255) NOT NULL;
 ALTER TABLE user_album_info change `priview_img` `preview_img` varchar(255) NOT NULL;