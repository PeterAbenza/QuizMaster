package com.QuizMaster.QuizMaster.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.QuizMaster.QuizMaster.model.Alternatives;

public interface AlternativesRepository extends JpaRepository<Alternatives, Integer> {
}

