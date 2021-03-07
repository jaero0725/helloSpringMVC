package kr.ac.hansung.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	// private static final Logger logger =
	// LoggerFactory.getLogger("kr.ac.hansung.controller.HomeController.java");

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {

		// Trace -> Debug -> Info -> Warn -> Error
		String url = request.getRequestURI().toString(); // url 찍기
		String clientIPaddr = request.getRemoteAddr(); // client ip 주소 찍기

		logger.info("Request URL : {}, Client IP: {}", url, clientIPaddr);
		
		return "home";
	}

}
