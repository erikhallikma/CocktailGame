package com.ridango.game.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CocktailApiResponse {
    private List<Cocktail> drinks;
}