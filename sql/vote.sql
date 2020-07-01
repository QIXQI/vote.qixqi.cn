-- 使用 j2ee 数据库
create database if not exists j2ee;
use j2ee;


-- 创建 vote 表
create table if not exists `vote`(
    -- 教师编号   
    `id` int(11) not null,
    `name` varchar(255) not null,
    `subject` varchar(255) not null,
    `sex` varchar(255) not null,
    `age` int(5) not null,
    -- 票数
    `poll` int(11) not null default 0,
    primary key(`id`)
) ENGINE=InnoDB default charset=utf8;



-- 更新 vote 表
-- 一次更新多条数据
update vote set poll = case id
    when 200234145 then 1
    when 200392677 then 2
    when 200452170 then 3
    end
where id in (200234145, 200392677, 200452170);  -- 这一行可以没有，不知道性能是否会有影响



-- 创建 vote_ip 表
create table if not exists `vote_ip`(
    -- 投票者ip
    `ip` varchar(255) not null,
    `last_vote_time` datetime not null,
    primary key(`ip`)
) ENGINE=InnoDB default charset=utf8;



-- 创建 user 表
create table if not exists `user`(
	`user_id` int(11) primary key,
	`user_nickname` varchar(255) not null,
	`user_password` varchar(255) not null,
	`user_sex` varchar(10) default "未知",
	`user_priority_id` int(5) default 0,
	`user_join_time` timestamp default current_timestamp(),
	`user_login_time` timestamp default current_timestamp(),
	`user_email` varchar(255) unique,
	`user_phone` varchar(15) unique,
	`user_status_id` int(5) default 0,
	`user_avatar` varchar(255) default "/vote/images/avatar/default.jpg",
	`user_birthday` date default "1970-01-01",
	foreign key(`user_priority_id`) references user_priority(`user_priority_id`)
	on delete cascade on update cascade,
	foreign key(`user_status_id`) references user_status(`user_status_id`)
	on delete cascade on update cascade,
	index index_id(`user_id`)
)ENGINE=InnoDB default charset=utf8;


-- 创建 user_priority 表
create table if not exists `user_priority`(
	`user_priority_id` int(5) primary key,
	`user_priority` varchar(255) not null unique,
	index index_id(`user_priority_id`)
)ENGINE=InnoDB default charset=utf8;


-- 创建 user_status 表
create table if not exists `user_status`(
	`user_status_id` int(5) primary key,
	`user_status` varchar(255) not null unique,
	index index_id(`user_status_id`)
)ENGINE=InnoDB default charset=utf8;



-- 创建 visit_log 表
create table if not exists `visit_log`(
	`visit_ip` varchar(255) not null,
	`visit_time` timestamp current_timestamp(),
	primary key(`visit_ip`, `visit_time`)
)ENGINE=InnoDB default charset=utf8;


-- 创建 login_log 表
create table if not exists `login_log`(
	`user_id` int(11),
	`login_ip` varchar(255),
	`login_time` timestamp current_timestamp(),
	primary key(`user_id`, `login_time`),
	foreign key(`user_id`) references user(`user_id`)
	on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;











