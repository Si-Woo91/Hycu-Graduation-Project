package com.han.admin.controller;

import java.security.Principal;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.han.admin.utill.CustomUtill;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping(value = "/")
	public String home(Locale locale
						, Model model
						, @RequestParam(value="error", required = false) String error
						, @RequestParam(value = "exception", required = false) String exception
						, Principal principal) {
		logger.info("Welcome home! The client locale is {}.", locale);
		logger.debug("로그인 페이지");

		// 로그인이 되어 있을시 admin 페이지로 이동
		if(!CustomUtill.isNullOrEmpty(principal)) {
			
			return "redirect:/userList";
		}
		
		/* 관리자 비밀번호 암호화
		 * BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		 * 
		 * String encodedPassword = passwordEncoder.encode("1234");;
		 * 
		 * System.out.println("Encoded Password: " + encodedPassword);
		 */
		
		logger.debug("err :: " + error);
		logger.debug("exception :: " + exception);
		
    	model.addAttribute("error", error);
    	model.addAttribute("exception", exception);

		return "login/login";
	}
	
	
}
