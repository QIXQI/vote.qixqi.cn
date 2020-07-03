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
	`user_id` int(11) primary key auto_increment,
	`user_name` varchar(255) not null unique,
	`user_password` varchar(255) not null,
	`user_sex` varchar(10) default "unkown",
	`user_priority_id` int(5) default 0,		-- 默认：注册用户
	`user_join_time` timestamp default CURRENT_TIMESTAMP,
	`user_login_time` timestamp default CURRENT_TIMESTAMP,
	`user_email` varchar(255) not null unique,
	`user_phone` varchar(15) not null unique,
	`user_status_id` int(5) default 0,			-- 默认：离线
	`user_avatar` varchar(255) default "/vote/images/avatar/default.jpg",	-- 默认头像
	`user_birthday` date default "1970-01-01",
	foreign key(`user_priority_id`) references user_priority(`user_priority_id`)
	on delete cascade on update cascade,
	foreign key(`user_status_id`) references user_status(`user_status_id`)
	on delete cascade on update cascade,
	index index_id(`user_id`)
)ENGINE=InnoDB default charset=utf8;


-- 查找用户
select user_id, user_name, user_sex, user_priority_id, user_join_time, user_login_time, user_email, user_phone, user_status_id, user_avatar, user_birthday
from user
where (user_name = ? or user_email = ? or user_phone = ?) and user_password = ?


-- 更新权限
update user u, user_priority up
set u.user_priority_id = up.user_priority_id
where u.user_id = ? and  up.user_priority = ?

-- 更新状态
update user u, user_status us
set u.user_status_id = us.user_status_id
where u.user_id = ? and us.user_status = ?


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
	`visit_time` timestamp default CURRENT_TIMESTAMP,
	primary key(`visit_ip`, `visit_time`)
)ENGINE=InnoDB default charset=utf8;


-- 创建 login_log 表
create table if not exists `login_log`(
	`user_id` int(11) not null,
	`login_ip` varchar(255),
	`login_time` timestamp default CURRENT_TIMESTAMP,
	primary key(`user_id`, `login_time`),
	foreign key(`user_id`) references user(`user_id`)
	on delete cascade on update cascade,
	index index_id(`user_id`)
)ENGINE=InnoDB default charset=utf8;

-- 查询用户最近登录
-- 下策
select *
from login_log
where user_id = ?
order by login_time desc
limit 1
-- 中策
-- group by 有些问题
select * 
from(
	select *
	from login_log
	where user_id = ?
	order by login_time desc
) as ll
group by ll.user_id
-- 上策
-- group by 有问题
select *, max(login_time) 
from login_log
where user_id = ?
group by user_id
-- 使用window function
-- 如何找到对应每个 user_id最大login_time的login_ip
select user_id, login_ip, max(login_time) over (partition by user_id) as user_last_login
from login_log;
-- 使用 window function
select user_id, FIRST_VALUE(login_ip) over(partition by user_id order by login_time desc) as lastLoginIp, max(login_time) over (partition by user_id) as lastLoginTime
from login_log
where user_id = ?
limit 1;
-- 使用子查询
select ll1.user_id, ll1.login_ip, ll1.login_time
from login_log as ll1, (select max(login_time) lastTime from login_log where user_id = ?) as ll2
where ll1.user_id = ? and ll1.login_time = ll2.lastTime;



-- 创建投票
create table if not exists `vote`(
	`vote_id` int(11) primary key auto_increment,
	`user_id` int(11) not null,
	`vote_name` varchar(255) not null,
	`vote_type` int(5) default 0,		-- 投票类型：0普通投票/1图片/2音频/3视频
	`vote_start_time` timestamp default CURRENT_TIMESTAMP,
	`vote_end_time` timestamp not null, -- 投票结束时间
	`vote_desc1` varchar(255),		-- 对应于选项，相当于字段名
	`vote_desc2` varchar(255),
	`vote_desc3` varchar(255),
	`vote_desc4` varchar(255),
	`vote_desc5` varchar(255),
	`option_number` int(11) default 0,
	foreign key(`user_id`) references user(`user_id`)
	on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;


-- 创建投票日志
create table if not exists `vote_log`(
	`vote_id` int(11) not null,
	`user_id` int(11),	-- 游客投票时为空
	`vote_ip` varchar(255) not null,
	`vote_time` timestamp default CURRENT_TIMESTAMP,
	foreign key(`vote_id`) references vote(`vote_id`)
	on delete cascade on update cascade,
	foreign key(`user_id`) references user(`user_id`)
	on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

-- 获取最新投票时间(vote_id, vote_ip)
select max(vote_time)
from vote_log
where vote_id = ? and vote_ip = ?



-- 创建普通投票选项
create table if not exists `normbal_option`(
	`option_id` int(11) primary key auto_increment,
	`vote_id` int(11) not null,
	`option_desc1` varchar(255) not null,
	`option_desc2` varchar(255),
	`option_desc3` varchar(255),
	`option_desc4` varchar(255),
	`option_desc5` varchar(255),
	`option_poll` int(11) default 0,
	foreign key(`vote_id`) references vote(`vote_id`)
	on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

-- 创建带有图片的投票选项
create table if not exists `img_option` (
	`option_id` int(11) primary key auto_increment,
	`vote_id` int(11) not null,
	`img_url` varchar(255) not null,
	`option_desc1` varchar(255),
	`option_desc2` varchar(255),
	`option_desc3` varchar(255),
	`option_desc4` varchar(255),
	`option_desc5` varchar(255),
	`option_poll` int(11) default 0,
	foreign key(`vote_id`) references vote(`vote_id`)
	on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

-- 创建带有音频的投票选项
create table if not exists `audio_option`(
	`option_id` int(11) primary key auto_increment,
	`vote_id` int(11) not null,
	`audio_url` varchar(255) not null,
	`option_desc1` varchar(255),
	`option_desc2` varchar(255),
	`option_desc3` varchar(255),
	`option_desc4` varchar(255),
	`option_desc5` varchar(255),
	`option_poll` int(11) default 0,
	foreign key(`vote_id`) references vote(`vote_id`)
	on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

-- 创建带有视频的投票选项
create table if not exists `video_option`(
	`option_id` int(11) primary key auto_increment,
	`vote_id` int(11) not null,
	`video_url` varchar(255) not null,
	`option_desc1` varchar(255),
	`option_desc2` varchar(255),
	`option_desc3` varchar(255),
	`option_desc4` varchar(255),
	`option_desc5` varchar(255),
	`option_poll` int(11) default 0,
	foreign key(`vote_id`) references vote(`vote_id`)
	on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;









