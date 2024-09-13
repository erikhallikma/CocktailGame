package com.ridango.game.service;

import com.ridango.game.model.Cocktail;
import com.ridango.game.model.GameModel;
import com.ridango.game.model.GameResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameService {

    private final CocktailApiService cocktailApiService;
    @Getter // for testing
    private GameModel game;

    public void startGame() {
        game = new GameModel();
        newCocktail();
    }

    public void newCocktail() {
        Cocktail cocktail;
        do {
            cocktail = cocktailApiService.getRandomCocktail();
        } while (cocktail == null || game.getUsedCocktails().contains(cocktail.getStrDrink()));
        game.setCorrect(false);
        game.setName(cocktail.getStrDrink());
        game.setInstructions(cocktail.getStrInstructions());
        game.setCategory(cocktail.getStrCategory());
        game.setGlass(cocktail.getStrGlass());
        game.setIngredients(cocktail.getIngredientsList());
        game.setImage(cocktail.getStrDrinkThumb());
        game.setAttemptsLeft(5);
        game.setHiddenCocktailName(game.getName().replaceAll("[^ ]", "_")); //replace everything but spaces
        game.addUsedCocktail(game.getName());
    }

    public void skip() {
        failAttempt();
    }

    public void guess(String guess) {
        if (game.isCorrect()) { // means person already guessed the drink but doesn't have a new one yet
            return;
        }
        boolean isMatching = guess.equalsIgnoreCase(game.getName());
        if (isMatching) {
            game.setCorrect(true);
            game.addScore();
            return;
        }
        failAttempt();
    }

    private void failAttempt() {
        game.removeAttempt();
        if (game.getAttemptsLeft() == 0) {
            game.setGameOver(true);
            return;
        }
        revealLetter();
        game.addHint();
    }

    private void revealLetter() {
        char[] hiddenArray = game.getHiddenCocktailName().toCharArray();
        char[] revealedArray = game.getName().toCharArray();
        List<Integer> unrevealedPositions = new ArrayList<>();

        for (int i = 0; i < hiddenArray.length; i++) {
            if (hiddenArray[i] == '_') {
                unrevealedPositions.add(i);
            }
        }

        int minUnrevealedPositions = 2;
        if (unrevealedPositions.size() <= minUnrevealedPositions) {
            return;
        }

        double n = 7.0; // for every n amount of unrevealed letters reveal an extra letter
        int letterAmountToReveal = (int) Math.ceil(unrevealedPositions.size() / n);

        Random random = new Random();
        for (int i = 0; i < letterAmountToReveal; i++) {
            int randomIndex = random.nextInt(unrevealedPositions.size());
            int positionToReveal = unrevealedPositions.get(randomIndex);

            hiddenArray[positionToReveal] = revealedArray[positionToReveal];
            unrevealedPositions.remove(randomIndex);
        }

        game.setHiddenCocktailName(new String(hiddenArray));
    }

    private void addHints(GameResponseDto.GameResponseDtoBuilder dtoBuilder) {
        int n = game.getAttemptsLeft();

        if (game.isCorrect()) { // reveal all hints if the drink was guessed correctly
            n = 1;
        }

        if (n <= 4) {
            dtoBuilder.glass(game.getGlass());
        }
        if (n <= 3) {
            dtoBuilder.category(game.getCategory());
        }
        if (n <= 2) {
            dtoBuilder.ingredients(game.getIngredients());
        }
        if (n <= 1) {
            dtoBuilder.image(game.getImage());
        }
    }

    public GameResponseDto getCurrentGameState() {
        GameResponseDto.GameResponseDtoBuilder dtoBuilder = GameResponseDto.builder();
        dtoBuilder.instructions(game.getInstructions())
                .score(game.getScore())
                .attemptsLeft(game.getAttemptsLeft())
                .correct(game.isCorrect())
                .gameOver(game.isGameOver());
        addHints(dtoBuilder);
        if (game.isGameOver() || game.isCorrect()) {
            dtoBuilder.name(game.getName());
        } else {
            dtoBuilder.name(game.getHiddenCocktailName());
        }
        return dtoBuilder.build();
    }
}
