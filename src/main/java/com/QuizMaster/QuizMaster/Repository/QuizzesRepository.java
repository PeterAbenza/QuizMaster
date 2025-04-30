package com.QuizMaster.QuizMaster.Repository;

import com.QuizMaster.QuizMaster.model.Quizzes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizzesRepository extends JpaRepository<Quizzes, Long> {
    // Aqui pode criar métodos personalizados depois, tipo buscar por categoria ou nível
}
