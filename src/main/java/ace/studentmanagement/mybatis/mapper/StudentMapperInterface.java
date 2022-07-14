package ace.studentmanagement.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ace.studentmanagement.mybatis.dto.Student;

@Mapper
public interface StudentMapperInterface {

	@Insert("Insert into student_table(id, name, dob, gender, phone, education, photo) values(#{id}, #{name}, #{dob}, #{gender}, #{phone}, #{education}, #{photo})")
	public int save(Student student);
	
	@Select("select * from student_table")
	List<Student> findAll();
	
	@Select("select * from student_table where id = #{id}")
	Student getById(String id);
	
	@Update("update student_table set name=#{name}, dob= #{dob}, gender=#{gender}, phone = #{phone}, education = #{education}, photo = #{photo} where id = #{id}")
	public void update(Student s);
	
	@Delete("delete from student_table where id = #{id}")
	public void deleteById(String id);
	
	 String sql = "select distinct student_table.id, student_table.name, student_table.dob,student_table.gender, student_table.phone, student_table.education, student_table.photo"
			  + " from student_table join student_course on student_table.id = student_course.student_id "
			  + "join course_table on student_course.course_id = course_table.id" 
			  + " where student_table.id = #{id} or student_table.name = #{name} or course_table.name = #{course} order by student_table.id" ; 
	@Select(sql)
	public List<Student> findStudentByIdOrNameOrCourses(String id, String name, String course);
}
