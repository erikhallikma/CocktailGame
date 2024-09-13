import {Component, OnInit} from '@angular/core';
import {GameComponent} from "./components/game/game.component";
import {HiscoresComponent} from "./components/hiscores/hiscores.component";
import {ApiService} from "./services/api.service";
import {GameResponse} from "./types/GameResponse.type";
import {GameOverComponent} from "./components/gameOver/gameOver.component";
import {NgIf} from "@angular/common";
import {WelcomeComponent} from "./components/welcome/welcome.component";

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [GameComponent, HiscoresComponent, GameOverComponent, WelcomeComponent, NgIf]
})

export class AppComponent implements OnInit {
  gameData!: GameResponse;
  constructor(public apiService: ApiService) {}
  ngOnInit() {
    this.apiService.gameData$.subscribe(data => {
      this.gameData = data;
    });
  }
}
