
  package ace.studentmanagement.mybatis.controller;
  
 import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
  
  import org.springframework.beans.factory.annotation.Autowired; import
  org.springframework.stereotype.Controller; import
  org.springframework.ui.ModelMap; import
  org.springframework.validation.BindingResult; import
  org.springframework.validation.annotation.Validated; import
  org.springframework.web.bind.annotation.GetMapping; import
  org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import
  org.springframework.web.bind.annotation.PostMapping; import
  org.springframework.web.bind.annotation.RequestParam; import
  org.springframework.web.servlet.ModelAndView;


import ace.studentmanagement.mybatis.dto.Course;
import ace.studentmanagement.mybatis.dto.Student;
import ace.studentmanagement.mybatis.mapper.CourseMapperInterface;
import ace.studentmanagement.mybatis.mapper.StudentCourseMapperInterface;
import ace.studentmanagement.mybatis.mapper.StudentMapperInterface;
import ace.studentmanagement.mybatis.model.StudentBean;


  
  @Controller 
  public class StudentController {
  
	  @Autowired 
	  private StudentMapperInterface studentService;
	  @Autowired
	  private CourseMapperInterface courseService;
	  @Autowired
	  private StudentCourseMapperInterface studentCourseService;
  
	  @GetMapping("/showStudentRegister") 
	  public ModelAndView showStudentRegister(HttpSession session) { 
		  if(session.getAttribute("user") == null) { 
			  return new ModelAndView("redirect:/"); 
			  }
		  else 
		  {	  
			  StudentBean studentBean = new StudentBean();
			  return new ModelAndView("STU001","studentBean",studentBean); 
			  } 
	 }
	  
	  @PostMapping("/studentRegister") 
	  public String studentRegister(@ModelAttribute("studentBean") @Validated StudentBean studentBean, BindingResult br, ModelMap model) { 
		  if(br.hasErrors()) { 
			  return "STU001"; 
			  } 
		  String id = idGenerator(); 
		  Student student = new Student(id, studentBean.getStudentName(), studentBean.getStudentDob(),studentBean.getStudentGender(), studentBean.getStudentPhone(),
				  			studentBean.getStudentEducation(),studentBean.getStudentPhoto()); 
		  ArrayList<String> courses = studentBean.getStudentCourse();
		  studentService.save(student);
		  for(String c: courses) {
			  studentCourseService.save(student.getId(), c);
		  }
		  //return "redirect:/showStudentAll";
		  model.addAttribute("success","Student Registered successfully.");
		  return "STU001";
		  }
	  
	  @GetMapping("/showStudentAll") 
	  public String showStudentAll(ModelMap model, HttpSession session) { 
		  if(session.getAttribute("user") == null) { 
			  return "redirect:/"; 
			  }
		  else 
			  {		  
			  List<Student> studentList = studentService.findAll(); 
			  Map<String, String> map = new HashMap<>();
			  for(Student student: studentList) { 
				  List<Course> selectedCourses = studentCourseService.selectCourseByStudentId(student.getId());
				  String courseName = "";
				  for(Course course: selectedCourses) {
					  if(courseName.isBlank()) {
						  courseName += course.getName();
					  }else courseName += ", "+ course.getName();
				  }
				  map.put(student.getId(), courseName); 
			  } 
			  model.addAttribute("map", map);
			  model.addAttribute("studentList", studentList); 
			  return "STU003"; 
			  } 
		}
	  
	  @GetMapping("/searchStudent") 
	  public String searchStudent(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("course") String course, ModelMap model) {
		  if(id.isBlank() && name.isBlank() && course.isBlank()) { 
			  return "redirect:/showStudentAll"; 
		  }else { 
			  List<Student> studentList = studentService.findStudentByIdOrNameOrCourses(id, name, course);
			  Map<String, String> map = new HashMap<>();
			  for(Student student: studentList) { 
				  List<Course> selectedCourses = studentCourseService.selectCourseByStudentId(student.getId());
				  String courseName = "";
				  for(Course c: selectedCourses) {
					  if(courseName.isBlank()) {
						  courseName += c.getName();
					  }else courseName += ", "+ c.getName();
				  }
				  map.put(student.getId(), courseName); 
			  } 
			  model.addAttribute("map", map);
		  model.addAttribute("studentList", studentList);
		  return "STU003"; 
		  } 
		 }
		  
	  @GetMapping("/seeMore/{id}") 
	  public ModelAndView seeMore(@PathVariable("id")String id) { 
		  Student student = studentService.getById(id);
		  ArrayList<String> list = new ArrayList<>();
		  List<Course> attendedCourse = studentCourseService.selectCourseByStudentId(student.getId());
		  for(Course c: attendedCourse) {
			  list.add(c.getId());
		  }
		  StudentBean studentBean = new StudentBean(
				  student.getId(), 
				  student.getName(),
				  student.getDob(),
				  student.getGender(),
				  student.getPhone(),
				  student.getEducation(), 
				  student.getPhoto(),
				  list
				  );
		  return new  ModelAndView("STU002","studentBean", studentBean); }
	  
	  @GetMapping("/deleteStudent") 
	  public String deleteStudent(@RequestParam("id") String id) {
		  studentService.deleteById(id);
		  return "redirect:/showStudentAll"; }
		  
	  @GetMapping("/showStudentUpdate") 
	  public ModelAndView showStudentUpdate(@RequestParam("id") String id) {
		  Student student = studentService.getById(id);
		  ArrayList<String> list = new ArrayList<>();
		  List<Course> attendedCourse = studentCourseService.selectCourseByStudentId(student.getId());
		  for(Course c: attendedCourse) {
			  list.add(c.getId());
		  }
		  StudentBean studentBean = new StudentBean(
				  student.getId(), 
				  student.getName(),
				  student.getDob(),
				  student.getGender(),
				  student.getPhone(),
				  student.getEducation(), 
				  student.getPhoto(),
				  list
				  );
		  return new ModelAndView("STU002-01","studentBean",studentBean); }
	  
	  @PostMapping("/updateStudent") 
	  public String updateStudent(@ModelAttribute("studentBean") @Validated StudentBean bean, BindingResult br) { 
		  if(br.hasErrors()) { return "STU002-01"; }	  
		  Student student = new Student(bean.getStudentId(), bean.getStudentName(), bean.getStudentDob(), bean.getStudentGender(), bean.getStudentPhone(), bean.getStudentEducation(), bean.getStudentPhoto() );
		  ArrayList<String> courses = bean.getStudentCourse();
		  studentService.update(student);
		  for(String c: courses) {
			  studentCourseService.save(student.getId(), c);
		  }
		  return "redirect:/showStudentAll";		  
	  }
	  
	  @ModelAttribute(value="courseList") 
	  public List<Course> courseList(){
		  ArrayList<String> list = new ArrayList<>(); 
		  List<Course> courseLists= courseService.getAllCourse(); 
		  for(Course course: courseLists) { 
			  list.add(course.getName()); 
			  } 
		  return courseLists;	  
	  } 
	  
	  public  String idGenerator() { 
		  String id = ""; 
		  List<Student> list = studentService.findAll();
		  if(list == null || list.size() <= 0) { 
			  id = "STU001"; 
		  }else {
			  Student lastDTO = list.get(list.size()-1); 
			  int lastId = Integer.parseInt(lastDTO.getId().substring(3)); 
			  id = String.format("STU"+"%03d", lastId+1); 
			  } 
		  return id; 
		  }
	  

 }
 