package ace.studentmanagement.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import ace.studentmanagement.mybatis.dto.Course;

@Mapper
public interface CourseMapperInterface {
	
	@Select("select * from course_table")
	public List<Course> getAllCourse();
	
	@Select("select * from course_table where id = #{id}")
	public Course findById(int id);
	
	@Insert("insert into course_table(id, name) values(#{id}, #{name})")
	public void save(Course course);
	
	@Select("select * from course_table where name=#{name}")
	public Course findByCourseName(String course);
	
}
