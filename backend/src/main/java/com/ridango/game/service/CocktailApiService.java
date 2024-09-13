package com.ridango.game.service;

import com.ridango.game.model.Cocktail;
import com.ridango.game.model.CocktailApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CocktailApiService {
    private final WebClient webClient;

    public CocktailApiService() {
        WebClient.Builder webClientBuilder = WebClient.builder();
        webClient = webClientBuilder.baseUrl("https://www.thecocktaildb.com/api/json/v1/1").build();
    }

    public Cocktail getRandomCocktail() {
        CocktailApiResponse response =  webClient.get()
                .uri("/random.php")
                .retrieve()
                .bodyToMono(CocktailApiResponse.class)
                .block();

        if (response == null) {
            return null;
        }
        return response.getDrinks().get(0);
    }
}
