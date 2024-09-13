import {ApiService} from "../../services/api.service";
import {Component} from "@angular/core";

@Component({
  selector: 'WelcomeComponent',
  standalone: true,
  templateUrl:'./welcome.component.html',
})
export class WelcomeComponent  {
  constructor(public apiService: ApiService) {}

  startGame() {
    this.apiService.startGame()
  }
}
