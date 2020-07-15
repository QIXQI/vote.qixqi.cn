package cn.qixqi.vote;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import cn.qixqi.vote.dao.BaseDao;

@Controller
public class Init extends BaseDao{
	
	private Logger logger = LogManager.getLogger(Init.class.getName());
	
    @RequestMapping("/init.do")
    public String init(HttpServletRequest request){
    	Connection conn = getConnection();
    	PreparedStatement pst = null;
    	String result = "init_result";
    	try {
    		// user_priority
    		String sql = "create table if not exists `user_priority`( " + 
    				"	`user_priority_id` int(5) primary key, " + 
    				"	`user_priority` varchar(255) not null unique, " + 
    				"	index index_id(`user_priority_id`) " + 
    				")ENGINE=InnoDB default charset=utf8";
    		pst = conn.prepareStatement(sql);
    		pst.executeUpdate();
    		
    		// user_status
    		sql = "create table if not exists `user_status`( " + 
    				"	`user_status_id` int(5) primary key, " + 
    				"	`user_status` varchar(255) not null unique, " + 
    				"	index index_id(`user_status_id`) " + 
    				")ENGINE=InnoDB default charset=utf8";
    		pst = conn.prepareStatement(sql);
    		pst.executeUpdate();
    		
    		// user
    		sql = "create table if not exists `user`( " + 
    				"	`user_id` int(11) primary key auto_increment, " + 
    				"	`user_name` varchar(255) not null unique, " + 
    				"	`user_password` varchar(255) not null, " + 
    				"	`user_sex` varchar(10) default \"unkown\", " + 
    				"	`user_priority_id` int(5) default 0, " + 
    				"	`user_join_time` timestamp default CURRENT_TIMESTAMP, " + 
    				"	`user_login_time` timestamp default CURRENT_TIMESTAMP, " + 
    				"	`user_email` varchar(255) not null unique, " + 
    				"	`user_phone` varchar(15) not null unique, " + 
    				"	`user_status_id` int(5) default 0, " + 
    				"	`user_avatar` varchar(255) default \"/vote/images/avatar/default.jpg\", " + 
    				"	`user_birthday` date default \"1970-01-01\", " + 
    				"	foreign key(`user_priority_id`) references user_priority(`user_priority_id`) " + 
    				"	on delete set null on update cascade, " + 
    				"	foreign key(`user_status_id`) references user_status(`user_status_id`) " + 
    				"	on delete set null on update cascade, " + 
    				"	index index_id(`user_id`) " + 
    				")ENGINE=InnoDB default charset=utf8";
    		pst = conn.prepareStatement(sql);
    		pst.executeUpdate();
    		
    		// user_bind
    		sql = "create table if not exists `user_bind`( " + 
    				"	`user_id` int(11) primary key, " + 
    				"	`qq_openid` varchar(255) not null, " + 
    				"	foreign key(`user_id`) references user(`user_id`) " + 
    				"	on delete cascade on update cascade, " + 
    				"	index index_id(`qq_openid`) " + 
    				")ENGINE=InnoDB default charset=utf8";
    		pst = conn.prepareStatement(sql);
    		pst.executeUpdate();
    		
    		// visit_log
    		sql = "create table if not exists `visit_log`( " + 
    				"	`visit_ip` varchar(255) not null, " + 
    				"	`visit_time` timestamp default CURRENT_TIMESTAMP, " + 
    				"	primary key(`visit_ip`, `visit_time`) " + 
    				")ENGINE=InnoDB default charset=utf8";
    		pst = conn.prepareStatement(sql);
    		pst.executeUpdate();
    		
    		// login_log
    		sql = "create table if not exists `login_log`( " + 
    				"	`user_id` int(11) not null, " + 
    				"	`login_ip` varchar(255), " + 
    				"	`login_time` timestamp default CURRENT_TIMESTAMP, " + 
    				"	primary key(`user_id`, `login_time`), " + 
    				"	foreign key(`user_id`) references user(`user_id`) " + 
    				"	on delete cascade on update cascade, " + 
    				"	index index_id(`user_id`) " + 
    				")ENGINE=InnoDB default charset=utf8";
    		pst = conn.prepareStatement(sql);
    		pst.executeUpdate();
    		
    		// vote
    		sql = "create table if not exists `vote`( " + 
    				"	`vote_id` int(11) primary key auto_increment, " + 
    				"	`user_id` int(11) not null, " + 
    				"	`vote_name` varchar(255) unique not null, " + 
    				"	`vote_type` int(5) default 0, " + 
    				"	`vote_start_time` timestamp default CURRENT_TIMESTAMP, " + 
    				"	`vote_end_time` timestamp not null, " + 
    				"	`vote_desc1` varchar(255), " + 
    				"	`vote_desc2` varchar(255), " + 
    				"	`vote_desc3` varchar(255), " + 
    				"	`vote_desc4` varchar(255), " + 
    				"	`vote_desc5` varchar(255), " + 
    				"	`option_number` int(11) default 0, " + 
    				"	foreign key(`user_id`) references user(`user_id`) " + 
    				"	on delete cascade on update cascade " + 
    				")ENGINE=InnoDB default charset=utf8";
    		pst = conn.prepareStatement(sql);
    		pst.executeUpdate();
    		
    		// vote_log
    		sql = "create table if not exists `vote_log`( " + 
    				"	`vote_id` int(11) not null, " + 
    				"	`user_id` int(11), " + 
    				"	`third_party_id` varchar(255), " + 
    				"	`vote_time` timestamp default CURRENT_TIMESTAMP, " + 
    				"	foreign key(`vote_id`) references vote(`vote_id`) " + 
    				"	on delete cascade on update cascade, " + 
    				"	foreign key(`user_id`) references user(`user_id`) " + 
    				"	on delete cascade on update cascade " + 
    				")ENGINE=InnoDB default charset=utf8";
    		pst = conn.prepareStatement(sql);
    		pst.executeUpdate();
    		
    		// normbal_option
    		sql = "create table if not exists `normbal_option`( " + 
    				"	`option_id` int(11) primary key auto_increment, " + 
    				"	`vote_id` int(11) not null, " + 
    				"	`option_desc1` varchar(255) not null, " + 
    				"	`option_desc2` varchar(255), " + 
    				"	`option_desc3` varchar(255), " + 
    				"	`option_desc4` varchar(255), " + 
    				"	`option_desc5` varchar(255), " + 
    				"	`option_poll` int(11) default 0, " + 
    				"	foreign key(`vote_id`) references vote(`vote_id`) " + 
    				"	on delete cascade on update cascade " + 
    				")ENGINE=InnoDB default charset=utf8";
    		pst = conn.prepareStatement(sql);
    		pst.executeUpdate();
    		
    		// img_option
    		sql = "create table if not exists `img_option` ( " + 
    				"	`option_id` int(11) primary key auto_increment, " + 
    				"	`vote_id` int(11) not null, " + 
    				"	`img_url` varchar(255) not null, " + 
    				"	`option_desc1` varchar(255), " + 
    				"	`option_desc2` varchar(255), " + 
    				"	`option_desc3` varchar(255), " + 
    				"	`option_desc4` varchar(255), " + 
    				"	`option_desc5` varchar(255), " + 
    				"	`option_poll` int(11) default 0, " + 
    				"	foreign key(`vote_id`) references vote(`vote_id`) " + 
    				"	on delete cascade on update cascade " + 
    				")ENGINE=InnoDB default charset=utf8";
    		pst = conn.prepareStatement(sql);
    		pst.executeUpdate();
    		
    		// audio_option
    		sql = "create table if not exists `audio_option`( " + 
    				"	`option_id` int(11) primary key auto_increment, " + 
    				"	`vote_id` int(11) not null, " + 
    				"	`audio_url` varchar(255) not null, " + 
    				"	`option_desc1` varchar(255), " + 
    				"	`option_desc2` varchar(255), " + 
    				"	`option_desc3` varchar(255), " + 
    				"	`option_desc4` varchar(255), " + 
    				"	`option_desc5` varchar(255), " + 
    				"	`option_poll` int(11) default 0, " + 
    				"	foreign key(`vote_id`) references vote(`vote_id`) " + 
    				"	on delete cascade on update cascade " + 
    				")ENGINE=InnoDB default charset=utf8";
    		pst = conn.prepareStatement(sql);
    		pst.executeUpdate();
    		
    		// video_option
    		sql = "create table if not exists `video_option`( " + 
    				"	`option_id` int(11) primary key auto_increment, " + 
    				"	`vote_id` int(11) not null, " + 
    				"	`video_url` varchar(255) not null, " + 
    				"	`option_desc1` varchar(255), " + 
    				"	`option_desc2` varchar(255), " + 
    				"	`option_desc3` varchar(255), " + 
    				"	`option_desc4` varchar(255), " + 
    				"	`option_desc5` varchar(255), " + 
    				"	`option_poll` int(11) default 0, " + 
    				"	foreign key(`vote_id`) references vote(`vote_id`) " + 
    				"	on delete cascade on update cascade " + 
    				")ENGINE=InnoDB default charset=utf8";
    		pst = conn.prepareStatement(sql);
    		pst.executeUpdate();
    		
    		// user_priority 插入数据
    		sql = "insert into user_priority(user_priority_id, user_priority) values (?, ?)";
    		pst = conn.prepareStatement(sql);
    		pst.setInt(1, -2);
    		pst.setString(2, "VISITOR");
    		pst.addBatch();
    		pst.setInt(1, -1);
    		pst.setString(2, "THIRD_PARTY_USER");
    		pst.addBatch();
    		pst.setInt(1, 0);
    		pst.setString(2, "USER");
    		pst.addBatch();
    		pst.setInt(1, 1);
    		pst.setString(2, "ADMINISTRATOR");
    		pst.addBatch();
    		pst.executeBatch();
    		
    		// user_status 插入数据
    		sql = "insert into user_status(user_status_id, user_status) values (?, ?)";
    		pst = conn.prepareStatement(sql);
    		pst.setInt(1, 0);
    		pst.setString(2, "OFFLINE");
    		pst.addBatch();
    		pst.setInt(1, 1);
    		pst.setString(2, "ONLINE");
    		pst.addBatch();
    		pst.executeBatch();
    		
    	} catch(SQLException se) {  
    		result = "init_error";
    		this.logger.error(se.getMessage());
    	}
    	closeAll(conn, pst, null);
        return result;
    }
}