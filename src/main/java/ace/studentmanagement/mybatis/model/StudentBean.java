package ace.studentmanagement.mybatis.model;

import java.util.ArrayList;

import javax.validation.constraints.NotEmpty;

import ace.studentmanagement.mybatis.dto.Course;


public class StudentBean {
	private String studentId;
	@NotEmpty
	private String studentName;
	@NotEmpty
	private String studentDob;
	@NotEmpty
	private String studentGender;
	@NotEmpty
	private	String studentPhone;
	@NotEmpty
	private String studentEducation;
	@NotEmpty
	private ArrayList<String> studentCourse;
	private String studentPhoto;
	
	public StudentBean() {
		
	}
	public StudentBean(String studentId, String studentName,String studentDob,String studentGender
			,String studentPhone, String studentEducation, String studentPhoto)
	{
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentDob = studentDob;
		this.studentGender = studentGender;
		this.studentPhone = studentPhone;
		this.studentEducation = studentEducation;
		this.studentPhoto = studentPhoto;
		
	}
	
	public StudentBean(String studentId, String studentName,String studentDob,String studentGender
			,String studentPhone, String studentEducation, String studentPhoto, ArrayList<String> studentCourse)
	{
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentDob = studentDob;
		this.studentGender = studentGender;
		this.studentPhone = studentPhone;
		this.studentEducation = studentEducation;
		this.studentPhoto = studentPhoto;
		this.studentCourse = studentCourse;
		
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentDob() {
		return studentDob;
	}
	public void setStudentDob(String studentDob) {
		this.studentDob = studentDob;
	}
	public String getStudentGender() {
		return studentGender;
	}
	public void setStudentGender(String studentGender) {
		this.studentGender = studentGender;
	}
	public String getStudentPhone() {
		return studentPhone;
	}
	public void setStudentPhone(String studentPhone) {
		this.studentPhone = studentPhone;
	}
	public String getStudentEducation() {
		return studentEducation;
	}
	public void setStudentEducation(String studentEducation) {
		this.studentEducation = studentEducation;
	}

	public String getStudentPhoto() {
		return studentPhoto;
	}
	public void setStudentPhoto(String studentPhoto) {
		this.studentPhoto = studentPhoto;
	}
	public ArrayList<String> getStudentCourse() {
		return studentCourse;
	}
	public void setStudentCourse(ArrayList<String> studentCourse) {
		this.studentCourse = studentCourse;
	}
}
