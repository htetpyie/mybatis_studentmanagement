package ace.studentmanagement.mybatis.model;

import javax.validation.constraints.NotEmpty;

public class CourseBean {
	private String courseId;
	@NotEmpty
	private String courseName;
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
