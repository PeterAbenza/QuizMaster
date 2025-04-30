package com.QuizMaster.QuizMaster.dto;

import java.util.List;
import com.QuizMaster.QuizMaster.model.Quizzes;

public class QuizForm {
    private String title;
    private String description;
    private String category;
    private Quizzes.Level level;
    private List<QuestionForm> questions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Quizzes.Level getLevel() {
        return level;
    }

    public void setLevel(Quizzes.Level level) {
        this.level = level;
    }

    public List<QuestionForm> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionForm> questions) {
        this.questions = questions;
    }
}

