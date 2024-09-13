package com.ridango.game.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class GameModel {

    private String name;
    private String instructions;
    private String category;
    private String glass;
    private List<String> ingredients;
    private String image;
    private int score;
    private int attemptsLeft;
    private String hiddenCocktailName;
    private boolean correct;
    private boolean gameOver;
    private int hints;
    private Set<String> usedCocktails = new HashSet<>();
    
    public void addScore() {
        score += attemptsLeft;
    }

    public void removeAttempt() {
        attemptsLeft--;
    }

    public void addHint() {
        hints++;
    }

    public void addUsedCocktail(String name) {
        usedCocktails.add(name);
    }


}
