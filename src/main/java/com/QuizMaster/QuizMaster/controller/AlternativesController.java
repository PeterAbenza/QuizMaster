package com.QuizMaster.QuizMaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.QuizMaster.QuizMaster.Repository.QuizzesRepository;
import com.QuizMaster.QuizMaster.model.Quizzes;

@Controller
public class AlternativesController {

    @Autowired
    private QuizzesRepository quizzesRepository;

    @GetMapping("/quiz/{id}")
    public ModelAndView showAlternatives(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("quiz/alternativas/index");

        // Usando o quizzesRepository para encontrar o quiz pelo id
        Quizzes quiz = quizzesRepository.findById(id).orElseThrow(); // quizzesRepository corretamente

        mv.addObject("quizId", id);
        mv.addObject("quiz", quiz); 
        return mv;
    }
}
