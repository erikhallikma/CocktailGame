package com.ridango.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridango.game.model.Cocktail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CocktailTest {

    private List<Cocktail> mockCocktails;

    @BeforeEach
    void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream input = getClass().getResourceAsStream("/cocktailMockData.json")) {
            mockCocktails = objectMapper.readValue(input, new TypeReference<>() {});
        }
    }

    @Test
    void getIngredientsList() {
        List<String> ingredients = mockCocktails.get(0).getIngredientsList();
        assertNotNull(ingredients);
        assertEquals(4, ingredients.size());
        assertEquals(Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt"), ingredients);

        ingredients = mockCocktails.get(1).getIngredientsList();
        assertNotNull(ingredients);
        assertEquals(5, ingredients.size());
        assertEquals(Arrays.asList("Light rum", "Lime", "Sugar", "Mint", "Soda water"), ingredients);
    }
}
