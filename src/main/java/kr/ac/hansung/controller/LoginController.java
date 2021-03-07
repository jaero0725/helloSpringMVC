package kr.ac.hansung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLogin(
			@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required=false) String logout,
			Model model){
		
		//로그인 실패한 경우
		if(error !=null) {
			model.addAttribute("errorMsg","Invalid username and password");
		}
		
		//로그아웃 시키는 경우
		if(logout !=null) {
			model.addAttribute("logoutMsg","You have been logged out successfully");
		}
		
		return "login";
	}
}
