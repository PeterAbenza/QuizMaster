package com.QuizMaster.QuizMaster.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.QuizMaster.QuizMaster.model.Results;

public interface ResultsRepository extends JpaRepository<Results, Integer> {
}
