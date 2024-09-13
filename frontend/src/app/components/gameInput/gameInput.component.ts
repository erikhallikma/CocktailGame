import {Component, OnInit} from "@angular/core";
import {NgIf} from "@angular/common";
import {ApiService} from "../../services/api.service";
import {GameResponse} from "../../types/GameResponse.type";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'GameInputComponent',
  standalone: true,
  templateUrl:'./gameInput.component.html',
  styleUrl: './gameInput.component.css',
  imports: [NgIf, FormsModule]
})

export class gameInputComponent implements OnInit {
  guessedName: string = "";
  guessed: boolean = false;
  gameData!: GameResponse;

  constructor(public apiService: ApiService) {}

  ngOnInit() {
    this.apiService.gameData$.subscribe(data => {
      this.gameData = data;
    });
  }

  guess(guess: string) {
    if (this.gameData.correct) { // means the drink was guessed already and this just routes enter press to bring up next drink
      this.next()
      return;
    }
    if (!guess || this.gameData.gameOver) {
      return;
    }
    this.guessed = true;
    this.apiService.guess(guess)
  }

  next() {
    this.guessed = false;
    this.guessedName = ""
    this.apiService.next()
  }

  skip() {
    this.apiService.skip();
  }
}
