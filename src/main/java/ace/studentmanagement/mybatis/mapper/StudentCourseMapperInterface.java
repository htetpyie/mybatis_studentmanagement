package ace.studentmanagement.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import ace.studentmanagement.mybatis.dto.Course;

@Mapper
public interface StudentCourseMapperInterface {
	
	@Insert("insert into student_course(student_id, course_id) values(#{student_id}, #{course_id})")
	public void save(String student_id, String course_id);
	
	@Select("select course_table.id, course_table.name from course_table join student_course on course_table.id = student_course.course_id where student_course.student_id = #{id} ")
	public List<Course> selectCourseByStudentId(String id);
	
	@Delete("delete from student_course where student_id = #{id}")
	 public int deleteCourseListByStudentId(String id);
}
