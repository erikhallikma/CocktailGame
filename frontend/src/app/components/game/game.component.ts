import {Component, OnInit} from '@angular/core';
import {ApiService} from "../../services/api.service";
import {FormsModule} from "@angular/forms";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {GameResponse} from "../../types/GameResponse.type";
import {gameInputComponent} from "../gameInput/gameInput.component";

@Component({
  selector: 'GameComponent',
  standalone: true,
  templateUrl:'./game.component.html',
  styleUrl: './game.component.css',
  imports: [FormsModule, NgIf, NgOptimizedImage, gameInputComponent]
})

export class GameComponent implements OnInit {

  gameData!: GameResponse;
  constructor(public apiService: ApiService) {}

  ngOnInit() {
    this.apiService.gameData$.subscribe(data => {
      this.gameData = data;
    });
  }

}
