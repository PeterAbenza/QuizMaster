package com.QuizMaster.QuizMaster.dto;

import java.util.List;

public class QuestionForm {
    private String text;
    private int correctIndex;
    private List<AlternativeForm> alternatives;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }

    public List<AlternativeForm> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<AlternativeForm> alternatives) {
        this.alternatives = alternatives;
    }
}

