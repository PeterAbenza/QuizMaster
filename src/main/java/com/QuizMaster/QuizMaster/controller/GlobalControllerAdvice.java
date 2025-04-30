package com.QuizMaster.QuizMaster.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.QuizMaster.QuizMaster.Repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@ControllerAdvice // atributos globais automaticamente para todos os controllers e views do projeto
public class GlobalControllerAdvice {
	@Autowired
	private UsersRepository usersRepository;
	
	@ModelAttribute("username")
	public String getUsername() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
	        Object principal = auth.getPrincipal();
	        if (principal instanceof UserDetails userDetails) {
	            String email = userDetails.getUsername();
	            return usersRepository.findByEmail(email)
	                .map(user -> {
	                    String nomeSemEspacos = user.getName().replaceAll("\\s+", "");
	                    return nomeSemEspacos.length() > 6
	                        ? nomeSemEspacos.substring(0, 6) + "..."
	                        : nomeSemEspacos;
	                })
	                .orElse(null);
	        }
	    }
	    return null;
	}


    @ModelAttribute("userRole")
    public String getUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getAuthorities().stream()
                    .findFirst()
                    .map(a -> a.getAuthority().replace("ROLE_", ""))
                    .orElse(null);
        }
        return null;
    }
}

