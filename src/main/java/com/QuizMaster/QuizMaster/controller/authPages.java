package com.QuizMaster.QuizMaster.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;


import com.QuizMaster.QuizMaster.Repository.QuizzesRepository;
import com.QuizMaster.QuizMaster.Repository.UsersRepository;
import com.QuizMaster.QuizMaster.model.Quizzes;
import com.QuizMaster.QuizMaster.model.Users;
import com.QuizMaster.QuizMaster.model.Users.Role;

import org.springframework.ui.Model;

@Controller
public class authPages{
	
	// IMPORT GERAL:
	@Autowired
	private QuizzesRepository quizzesRepository;
	
	@Autowired
    private UsersRepository usersRepository;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	// HOME EM GERAL:
	
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
	
	// LOGIN:
	
	@GetMapping("/login")
	public ModelAndView showLogin(@RequestParam(value = "error", required = false) String error) {
	    ModelAndView mv = new ModelAndView("login/index");
	    if (error != null) {
	        mv.addObject("errorMessage", "Email ou senha inválidos.");
	    }
	    return mv;
	}
	
	// REGISTRO:
	
	@GetMapping("/criar-conta")
	public ModelAndView showCreateAccountForm() {
	    return new ModelAndView("registro/index");
	}
	
	@PostMapping("/criar-conta")
	public ModelAndView showRegistro(
			@RequestParam String name, 
			@RequestParam String email, 
			@RequestParam String password, 
			@RequestParam String confirmPassword) {
		
		ModelAndView mv = new ModelAndView("registro/index");
		
		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
		    mv.addObject("errorMessage", "Formato de email inválido.");
		    return mv;
		}
		
		
		
		// Verificando se nome é acima de 2
		if (name.length() < 3) {
		    mv.addObject("errorMessage", "O nome deve ter pelo menos 3 caracteres.");
		    return mv;
		}

		// Verificando se o domínio é válido, caso contrário, retornando erro
		String[] validDomains = {"gmail.com", "hotmail.com", "yahoo.com"};
		String emailDomain = email.substring(email.indexOf("@") + 1);

		boolean validDomain = false;
		for (String domain : validDomains) {
		    if (emailDomain.equalsIgnoreCase(domain)) {
		        validDomain = true;
		        break;
		    }
		}

		if (!validDomain) {
		    mv.addObject("errorMessage", "Domínio de e-mail inválido. Use um e-mail válido.");
		    return mv;
		}
	    
	    // Verifica se as senhas são iguais
	    if (!password.equals(confirmPassword)) {
	        mv.addObject("errorMessage", "As senhas não coincidem.");
	        return mv;
	    }
		
		// Verifica se o email já está registrado
		if(usersRepository.findByEmail(email).isPresent()) {
			mv.addObject("errorMessage", "Email já registrado.");
			return mv;
		}
		
		// Cria e salva o novo usuário
		Users newUser = new Users();
		newUser.setName(name);
		newUser.setEmail(email);
		newUser.setPassword(passwordEncoder.encode(password));
		newUser.setRole(Role.JOGADOR);
		
		usersRepository.save(newUser);
		
		// Redireciona para a tela de login após sucesso
		mv.setViewName("redirect:/login");
		return mv;
	}
	
	
}
