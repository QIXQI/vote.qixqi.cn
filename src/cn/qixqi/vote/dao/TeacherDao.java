package cn.qixqi.vote.dao;

import java.util.List;
import cn.qixqi.vote.entity.Teacher;

public interface TeacherDao {
	public String addTeacher(Teacher teacher);
	public List<Teacher> getTeachers();
	public String addPoll();
}
