package com.ridango.game.controller;

import com.ridango.game.model.GameResponseDto;
import com.ridango.game.model.Hiscore;
import com.ridango.game.model.HiscoreRequest;
import com.ridango.game.service.GameService;
import com.ridango.game.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game/api")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final DatabaseService databaseService;

    @GetMapping("/start")
    public ResponseEntity<GameResponseDto> startGame() {
        gameService.startGame();
        return ResponseEntity.ok(gameService.getCurrentGameState());
    }

    @PostMapping("/guess")
    public ResponseEntity<GameResponseDto> guess(@RequestBody String request) {
        gameService.guess(request);
        return ResponseEntity.ok(gameService.getCurrentGameState());
    }

    @GetMapping("/skip")
    public ResponseEntity<GameResponseDto> skip() {
        gameService.skip();
        return ResponseEntity.ok(gameService.getCurrentGameState());
    }

    @GetMapping("/next")
    public ResponseEntity<GameResponseDto> next() {
        gameService.newCocktail();
        return ResponseEntity.ok(gameService.getCurrentGameState());
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> hiscore(@RequestBody HiscoreRequest request) {
        databaseService.saveHiscore(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/hiscores")
    public ResponseEntity<List<Hiscore>> getHiscores() {
        return ResponseEntity.ok(databaseService.getTop10Scores());
    }
}
