package com.QuizMaster.QuizMaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;

import com.QuizMaster.QuizMaster.Repository.QuizzesRepository;
import com.QuizMaster.QuizMaster.Repository.UsersRepository;
import com.QuizMaster.QuizMaster.model.Quizzes;

@Controller
public class HomeController{
	
	@Autowired
	private QuizzesRepository quizzesRepository;
	
	@Autowired
    private UsersRepository usersRepository;
	
	@GetMapping("/")
	public ModelAndView showHome(@RequestParam(value = "page", defaultValue = "0") Integer page) {
		// página atual e o número de quizzes por página
		Pageable pageable = PageRequest.of(page, 9); // 9 quizzes por página
		Page<Quizzes> quizzesPage = quizzesRepository.findAll(pageable);

		ModelAndView mv = new ModelAndView("home/index");
		mv.addObject("quizzes", quizzesPage.getContent()); // quizzes para a view
		mv.addObject("totalPages", quizzesPage.getTotalPages()); // total de páginas
		mv.addObject("currentPage", page); // página atual
		
		
		// Verifica se o usuário está autenticado e busca o nome real
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    if (authentication != null && authentication.isAuthenticated()) { // usuario != vazio
	    	
	        Object principal = authentication.getPrincipal();
	        
	        if (principal instanceof UserDetails) {
	            String email = ((UserDetails) principal).getUsername();
	           
	            usersRepository.findByEmail(email)
                .ifPresent(user -> {
                    String nomeSemEspacos = user.getName().replaceAll("\\s+", ""); // remove espaços vazios
                    String nomeExibicao = nomeSemEspacos.length() > 6
                        ? nomeSemEspacos.substring(0, 6) + "..."
                        : nomeSemEspacos;
                    mv.addObject("username", nomeExibicao);
                    
                    mv.addObject("userRole", user.getRole().name()); // Ex: ADM ou JOGADOR
                });
	        }
	    }

		return mv;
	}
}
