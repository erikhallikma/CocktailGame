package com.ridango.game.model;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cocktail {
    private String strDrink;
    private String strInstructions;
    private String strCategory;
    private String strGlass;
    private String strDrinkThumb;
    private String strIngredient1;
    private String strIngredient2;
    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;
    private String strIngredient6;
    private String strIngredient7;
    private String strIngredient8;
    private String strIngredient9;
    private String strIngredient10;
    private String strIngredient11;
    private String strIngredient12;
    private String strIngredient13;
    private String strIngredient14;
    private String strIngredient15;

    public List<String> getIngredientsList() {
        List<String> ingredients = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.getName().startsWith("strIngredient") && field.get(this) != null) {
                    ingredients.add(field.get(this).toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return ingredients;
    }
}
