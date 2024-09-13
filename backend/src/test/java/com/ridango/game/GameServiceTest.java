package com.ridango.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridango.game.model.Cocktail;
import com.ridango.game.service.CocktailApiService;
import com.ridango.game.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameServiceTest {
    private GameService gameService;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Cocktail> mockCocktails;
        try (InputStream input = getClass().getResourceAsStream("/cocktailMockData.json")) {
            mockCocktails = objectMapper.readValue(input, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CocktailApiService cocktailApiService = mock(CocktailApiService.class);
        when(cocktailApiService.getRandomCocktail())
                .thenReturn(null)
                .thenReturn(mockCocktails.get(0))
                .thenReturn(mockCocktails.get(0))
                .thenReturn(mockCocktails.get(0))
                .thenReturn(mockCocktails.get(0))
                .thenReturn(mockCocktails.get(0))
                .thenReturn(mockCocktails.get(0))
                .thenReturn(mockCocktails.get(0))
                .thenReturn(mockCocktails.get(0))
                .thenReturn(mockCocktails.get(1));

        gameService = new GameService(cocktailApiService);
        gameService.startGame();
    }

    @Test
    void AvoidNullOrDuplicateCocktails() {
        assertEquals("Margarita", gameService.getGame().getName());

        // Second call to newCocktail() should skip duplicates and select mojito
        gameService.newCocktail();
        assertNotEquals("Margarita", gameService.getGame().getName());
        assertFalse(gameService.getGame().getName().isEmpty());
        assertEquals("Mojito", gameService.getGame().getName());

    }
}
