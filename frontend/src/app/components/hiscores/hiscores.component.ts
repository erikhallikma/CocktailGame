import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {ApiService} from "../../services/api.service";

@Component({
  selector: 'HiscoresComponent',
  standalone: true,
  templateUrl: './hiscores.component.html',
  styleUrl: '../../app.component.css',
  imports: [FormsModule]
})

export class HiscoresComponent implements OnInit {
  constructor(public apiService: ApiService) {}

  ngOnInit() {
    this.apiService.getHiscores();
  }

  newGame() {
    this.apiService.startGame();
  }
}
