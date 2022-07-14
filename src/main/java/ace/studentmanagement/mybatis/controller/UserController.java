package ace.studentmanagement.mybatis.controller;
  

  
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import ace.studentmanagement.mybatis.dto.User;
import ace.studentmanagement.mybatis.mapper.UserMapperInterface;
import ace.studentmanagement.mybatis.model.UserBean;

  

  
  @Controller 
  public class UserController {
  
	  @Autowired 
	  private UserMapperInterface userService;
	  
	  
	  @GetMapping(value="/displayUserRegister")
	  public ModelAndView displayUserRegister(ModelMap map) { 
		  UserBean userBean = new UserBean(); 
		  return new ModelAndView("USR001","userBean",userBean); 
		  }
	  
	  @PostMapping("/userRegister") 
	  public String userRegister(@ModelAttribute("userBean") @Validated UserBean userBean,  BindingResult br, ModelMap model ) { 
		  if(br.hasErrors()) { 
			  return "USR001";
		  	}
		  else if(!userBean.getUserPassword().equals(userBean.getUserCfPassword())) {			  
			  model.addAttribute("passwordError","Password doesn't match!"); 
			  return "USR001"; 
		  } else if(isEmailExist(userBean.getUserEmail())) {			  
			  model.addAttribute("error","Email already exists."); 
			  return "USR001"; 
		  }else {
			  
			  User user = new User( idGenerator() ,userBean.getUserName(), userBean.getUserEmail(), userBean.getUserCfPassword(), userBean.getUserRole());
			  userService.save(user);			
			  model.addAttribute("success","Successfully registered."); 
			  return "USR001"; 
			  }
	  
	  		}
	  
		
		  @GetMapping("/showUser") public String showUser(ModelMap model, HttpSession session) { 
			  if(session.getAttribute("user") == null) { 
				  return "redirect:/";
			  }else 
			  { 
				  List<User> list = userService.selectAll();
				  model.addAttribute("userList",list); 
				  return "USR003"; }
			  }
		  
			  @GetMapping("/showAddUser") 
			  public ModelAndView showAddUserPage() { 
				  UserBean userBean = new UserBean(); 
				  return new ModelAndView("USR001-01","userBean",userBean); 
				  }
			  
			  @PostMapping("/userAdd") 
			  public String userAdd(@ModelAttribute("userBean") @Validated UserBean userBean, BindingResult br, ModelMap model ) { 
				  if(br.hasErrors()) { 
					  return "USR001-01";
				  } else if(!userBean.getUserPassword().equals(userBean.getUserCfPassword())) {
					  
					  model.addAttribute("passwordError","Password doesn't match!"); 
					  return "USR001-01"; 
				  } 
				  else if(isEmailExist(userBean.getUserEmail())) {			  
					  model.addAttribute("error","Email already exists."); 
					  return "USR001-01"; 
				  }else 
					  {
					  User user = new User(idGenerator(),userBean.getUserName(), userBean.getUserEmail(), userBean.getUserCfPassword(), userBean.getUserRole());
					  userService.save(user);
					  model.addAttribute("success","Successfully registered."); 
					  return "redirect:/showUser"; 
					  }
			  
			  		}
//		  
		  @GetMapping("/userSearch") 
			  public String userSearch(ModelMap model, @RequestParam("id") String id, @RequestParam("name") String name) {
				  if(name.isBlank() && id.isBlank()) { 
					  return "redirect:/showUser"; 
					  } else {
						  List<User> list = userService.searchByIdOrName(id, name); 
						  model.addAttribute("userList", list); 
						  return "USR003";
					  } 
			  }
			  
			  @GetMapping("/showUserUpdate") 
			  public ModelAndView showUserUpdate(ModelMap model, @RequestParam("id") String id) { 
				  User userRes = userService.selectUser(id);
				  UserBean userBean = new UserBean(userRes.getId(), userRes.getName(), userRes.getEmail(), userRes.getPassword(), userRes.getRole());
				  //model.addAttribute("userBean", userRes); 
				  return new ModelAndView("USR002","userBean", userBean); 
				  }
//			  
			  @PostMapping("/userUpdate") 
			  public String userUpdate(@ModelAttribute("userBean") @Validated UserBean userBean, BindingResult br, HttpSession session, ModelMap model) {
			  User usr = userService.selectUser(userBean.getUserId());
			  String email = usr.getEmail();
			  if(br.hasErrors() ) {
				  return "USR002"; 
			  } else if(!userBean.getUserPassword().equals(userBean.getUserCfPassword())) { 
				  model.addAttribute("passwordError", "Password doesn't match." ); 
				  return "USR002"; 
			  } else if(isEmailExist(userBean.getUserEmail()) && !userBean.getUserEmail().equals(email)){
				  model.addAttribute("error","Email error"); 
				  return "USR002";
			  }
			  else { 
				  User user = new User(userBean.getUserId(),userBean.getUserName(), userBean.getUserEmail(), userBean.getUserPassword(), userBean.getUserRole()); 
				  userService.update(user);
				  return "redirect:/showUser"; } }
			  
			  @RequestMapping("/userDelete") 
			  public String userDelete(@RequestParam("id") String id) { 
				  userService.delete(id);
				  return "redirect:/showUser"; }
			  
//			  
			 
		  public  String idGenerator() { 
			  String id = ""; 
			  List<User> list = userService.selectAll();
			  if(list == null || list.size() <= 0) { 
				  id = "STU001"; 
			  }else {
				  User lastDTO = list.get(list.size()-1); 
				  int lastId = Integer.parseInt(lastDTO.getId().substring(3)); 
				  id = String.format("USR"+"%03d", lastId+1); 
				  } 
			  return id; 
			  }
	  
		  public Boolean isEmailExist(String email) {
			  User u = userService.selectByEmail(email);
			  return u != null;
		  }
  
  }
 