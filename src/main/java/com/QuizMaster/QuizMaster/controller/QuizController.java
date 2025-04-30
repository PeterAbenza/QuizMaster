package com.QuizMaster.QuizMaster.controller;

import com.QuizMaster.QuizMaster.Repository.AlternativesRepository;
import com.QuizMaster.QuizMaster.Repository.QuestionsRepository;
import com.QuizMaster.QuizMaster.Repository.QuizzesRepository;
import com.QuizMaster.QuizMaster.dto.AlternativeForm;
import com.QuizMaster.QuizMaster.dto.QuestionForm;
import com.QuizMaster.QuizMaster.dto.QuizForm;
import com.QuizMaster.QuizMaster.model.Alternatives;
import com.QuizMaster.QuizMaster.model.Questions;
import com.QuizMaster.QuizMaster.model.Quizzes;
import com.QuizMaster.QuizMaster.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal; // caso use Spring Security
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/quiz")
public class QuizController {

    @Autowired
    private QuizzesRepository quizzesRepository;
    
    @Autowired
    private QuestionsRepository questionRepository;
    
    @Autowired
    private AlternativesRepository alternativeRepository;

    @GetMapping("/criar")
    public ModelAndView CreateQuiz() {
        return new ModelAndView("quiz/index");
    }

    @PostMapping("/criar")
    public ModelAndView SalvarQuiz(
        @RequestParam String title,
        @RequestParam String description,
        @RequestParam String category,
        @RequestParam Quizzes.Level level,
        @AuthenticationPrincipal Users usuarioLogado // precisa do Spring Security configurado
    ) {
        Quizzes quiz = new Quizzes();
        quiz.setTitle(title);
        quiz.setDescription(description);
        quiz.setCategory(category);
        quiz.setLevel(level);
        quiz.setAuthor(usuarioLogado); // pega o usuário logado como autor

        quizzesRepository.save(quiz);

        return new ModelAndView("redirect:/admin/quiz/criar");
    }
    
    @PostMapping("/criar-completo")
    public ModelAndView salvarQuizCompleto( 
    		@ModelAttribute QuizForm quizDTO,
            @AuthenticationPrincipal Users usuarioLogado) {
    	
    	// Cria e salva o quiz
    	Quizzes quiz = new Quizzes();
    	quiz.setTitle(quizDTO.getTitle());
    	quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setCategory(quizDTO.getCategory());
        quiz.setLevel(quizDTO.getLevel());
        quiz.setAuthor(usuarioLogado);
        quizzesRepository.save(quiz);
        
     // Cria e salva as perguntas e alternativas
        for (QuestionForm questionDTO : quizDTO.getQuestions()) {
            Questions question = new Questions();
            question.setQuestionText(questionDTO.getText());
            question.setQuiz(quiz);
            questionRepository.save(question);

            for (int i = 0; i < questionDTO.getAlternatives().size(); i++) {
                AlternativeForm alternativeDTO = questionDTO.getAlternatives().get(i);
                Alternatives alternative = new Alternatives();
                alternative.setAlternativeText(alternativeDTO.getText());
                alternative.setIsCorrect(i == questionDTO.getCorrectIndex());
                alternative.setQuestion(question);
                alternativeRepository.save(alternative);
            }
        }
    	
 
    	return new ModelAndView("redirect:/admin/quiz/criar");
    }
}
