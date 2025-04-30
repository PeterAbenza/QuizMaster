package com.QuizMaster.QuizMaster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	@GetMapping("/login")
	public ModelAndView showLogin(@RequestParam(value = "error", required = false) String error) {
	    ModelAndView mv = new ModelAndView("login/index");
	    if (error != null) {
	        mv.addObject("errorMessage", "Email ou senha inválidos.");
	    }
	    return mv;
	}
}
