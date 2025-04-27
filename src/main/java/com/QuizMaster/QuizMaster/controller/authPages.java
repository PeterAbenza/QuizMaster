package com.QuizMaster.QuizMaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.QuizMaster.QuizMaster.Repository.QuizzesRepository;
import com.QuizMaster.QuizMaster.model.Quizzes;

@Controller
public class authPages{
	
	@Autowired
	private QuizzesRepository quizzesRepository;
	
	@GetMapping("/")
	public ModelAndView showHome(@RequestParam(value = "page", defaultValue = "0") Integer page) {
		// Defina a página atual e o número de quizzes por página
		Pageable pageable = PageRequest.of(page, 9); // 9 quizzes por página
		Page<Quizzes> quizzesPage = quizzesRepository.findAll(pageable);

		ModelAndView mv = new ModelAndView("home/index");
		mv.addObject("quizzes", quizzesPage.getContent()); // Passa os quizzes para a view
		mv.addObject("totalPages", quizzesPage.getTotalPages()); // Passa o total de páginas
		mv.addObject("currentPage", page); // Passa a página atual
		return mv;
	}
	
	@GetMapping("/login")
	public ModelAndView showLogin() {
		ModelAndView mv = new ModelAndView("login/index");
		return mv;
	}
	
	@GetMapping("/criar-conta")
	public ModelAndView showRegistro() {
		ModelAndView mv = new ModelAndView("registro/index");
		return mv;
	}
}
