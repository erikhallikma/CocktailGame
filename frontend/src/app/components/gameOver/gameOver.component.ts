import {Component, OnInit} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {ApiService} from "../../services/api.service";
import {GameResponse} from "../../types/GameResponse.type";

declare var window: any;

@Component({
  selector: 'GameOverComponent',
  standalone: true,
  templateUrl:'./gameOver.component.html',
  imports: [FormsModule, NgIf, NgOptimizedImage]
})

export class GameOverComponent implements OnInit {
  gameData!: GameResponse;
  playerName: string = "";
  private modal: any;
  nameTooLongError: boolean = false;

  constructor(public apiService: ApiService) {}
  ngOnInit() {
    this.apiService.gameData$.subscribe(data => {
      if (data.gameOver) {
        this.gameData = data;
        this.openModal()
      }
    });
  }

  openModal() {
    const modalElement = document.getElementById('gameOverModal');
    if (modalElement) {
      this.modal = new window.bootstrap.Modal(modalElement);
      this.modal.show();
    }
  }

  closeModal() {
    if (this.modal) {
      this.modal.hide();
    }
  }

  submit(name: string) {
    if (!name) {
      return;
    }
    if (name.length > 30) {
      this.nameTooLongError = true;
      return;
    }
    this.nameTooLongError = false;
    this.apiService.submitHiscore(name, this.gameData.score)
    this.closeModal()
    this.apiService.showHiscores = true
  }
}
