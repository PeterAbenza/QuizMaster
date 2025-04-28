package com.QuizMaster.QuizMaster.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.QuizMaster.QuizMaster.model.Users;

//JpaRepository métodos para fazer consultas no banco de dados.
public interface UsersRepository extends JpaRepository<Users, Integer> { 
	Optional<Users> findByEmail(String email); // Para validar se o email já existe
}
