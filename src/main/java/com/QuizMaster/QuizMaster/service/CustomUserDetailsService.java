package com.QuizMaster.QuizMaster.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.QuizMaster.QuizMaster.Repository.UsersRepository;
import com.QuizMaster.QuizMaster.model.Users;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> userOptional = usersRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Usuário não encontrado com email: " + email);
        }

        Users user = userOptional.get();
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .roles(user.getRole().name()) // Tipo: ADMIN ou JOGADOR
            .build();
    }
}
