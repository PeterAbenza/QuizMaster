package com.QuizMaster.QuizMaster.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.QuizMaster.QuizMaster.model.Answers;

public interface AnswersRepository extends JpaRepository<Answers, Integer> {
}

