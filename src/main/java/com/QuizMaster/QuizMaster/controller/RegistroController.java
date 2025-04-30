package com.QuizMaster.QuizMaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;

import com.QuizMaster.QuizMaster.Repository.UsersRepository;
import com.QuizMaster.QuizMaster.model.Users;
import com.QuizMaster.QuizMaster.model.Users.Role;

@Controller
public class RegistroController {
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
    private UsersRepository usersRepository;
	
	@GetMapping("/criar-conta")
	public ModelAndView showRegistro() {
	    return new ModelAndView("registro/index");
	}
	
	@GetMapping("/adm/criar-conta")
	public ModelAndView showRegistroAdm() {
	    return new ModelAndView("registro/adm");
	}
	
	// add user cliente
	@PostMapping("/criar-conta")
	public ModelAndView RegistroCliente(
			@RequestParam String name, 
			@RequestParam String email, 
			@RequestParam String password, 
			@RequestParam String confirmPassword,
			@RequestParam(required = false) String role) {
		
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
	
	
	// add user adm
	@PostMapping("/adm/criar-conta")
	public ModelAndView RegistroAdm(@RequestParam String name, 
	                                  @RequestParam String email, 
	                                  @RequestParam String password, 
	                                  @RequestParam String confirmPassword) {
	    ModelAndView mv = new ModelAndView("registro/adm");

	    // Validações do formulário
	    if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
	        mv.addObject("errorMessage", "Formato de email inválido.");
	        return mv;
	    }
	    if (name.length() < 3) {
	        mv.addObject("errorMessage", "O nome deve ter pelo menos 3 caracteres.");
	        return mv;
	    }
	    if (!password.equals(confirmPassword)) {
	        mv.addObject("errorMessage", "As senhas não coincidem.");
	        return mv;
	    }
	    if (usersRepository.findByEmail(email).isPresent()) {
	        mv.addObject("errorMessage", "Email já registrado.");
	        return mv;
	    }

	    // Cria o novo usuário como JOGADOR
	    Users newUser = new Users();
	    newUser.setName(name);
	    newUser.setEmail(email);
	    newUser.setPassword(passwordEncoder.encode(password));
	    newUser.setRole(Users.Role.ADM);

	    usersRepository.save(newUser);

	    mv.setViewName("redirect:/login");
	    return mv;
	}
}
