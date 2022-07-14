package ace.studentmanagement.mybatis.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ace.studentmanagement.mybatis.dto.User;
import ace.studentmanagement.mybatis.mapper.UserMapperInterface;
import ace.studentmanagement.mybatis.model.UserBean;



@Controller
public class Authentication {
	@Autowired
	private UserMapperInterface userService ;
	
		@GetMapping("/")
		public String displayLogin() {
			return "LGN001";
		}
	
	  @GetMapping("/showMenu") 
	  public String showMenu(HttpSession session){ 
		  if(session.getAttribute("user") == null) { 
			  return "redirect:/"; 
			  }
		  else return "MNU001"; 
		  }
	  
	  @PostMapping("/Login") 
	  public String login(ModelMap model, @RequestParam("id") String id, @RequestParam("password") String password, HttpSession session) { 
		  String current_date = now();
		  if(id.isEmpty() || password.isEmpty()) {
			  model.addAttribute("error", "Fill the data! "); 
			  return "LGN001"; 
		  }
		  User user = userService.selectByEmailAndPassword(id, password); 
		  if(user != null) {
			  UserBean userBean= new UserBean(
					  user.getId(),
					  user.getName(),
					  user.getEmail(),
					  user.getRole()
					  );
			  session.setAttribute("user", userBean); 
			  session.setAttribute("isLogin","Okay");
			  session.setAttribute("date", current_date); 
			  return "redirect:/showMenu"; 
			  }
		  model.addAttribute("error", "Please check your data again! "); 
		  return "LGN001"; 
	  }
		 
	  
	  @GetMapping("/Logout") 
	  public String logout(HttpSession session) {
		  session.setAttribute("user", ""); 
		  session.setAttribute("isLogin","");
		  session.setAttribute("date", ""); 
		  session.invalidate(); 
		  return "LGN001"; }
	 
	  public static String now() {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY.MM.dd");
			LocalDateTime now = LocalDateTime.now();
			String date = dtf.format(now);
			return date;
		}
}
