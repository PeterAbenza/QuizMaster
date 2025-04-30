package com.QuizMaster.QuizMaster.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.QuizMaster.QuizMaster.model.Questions;

public interface QuestionsRepository extends JpaRepository<Questions, Integer> {
}

