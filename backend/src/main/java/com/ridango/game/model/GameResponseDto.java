package com.ridango.game.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GameResponseDto {
    private String name;
    private String instructions;
    private String glass;
    private String category;
    private List<String> ingredients;
    private String image;
    private int score;
    private int attemptsLeft;
    private boolean correct; // was the guess correct
    private boolean gameOver;
}
