package com.cinker.action;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cinker.model.User;
import com.cinker.service.UserService;

@Controller
@RequestMapping("/user")
public class UserAction {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/login", method = { GET, POST })
	public String login(String username,String password,Model model,HttpSession session){
		String result = "login";
		if(username != null){
			try {
				username = new String(username.getBytes("ISO-8859-1"),"UTF-8");
				User user = userService.userLogin(username, password);
				if(user != null){
					session.setAttribute("username", username);
					model.addAttribute("username", user.getName());
					result = "admin/home";
				}else{
					model.addAttribute("message", "用户名密码错误！");
					result = "admin/login";
				}
			} catch (Exception e) {
				model.addAttribute("message", "网络异常");
				result = "admin/login";
			}
		}
		
		return result;
	}
	

}
