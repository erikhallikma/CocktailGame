import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GameResponse} from "../types/GameResponse.type";
import {BehaviorSubject} from "rxjs";
import {Hiscore} from "../types/Hiscore.type";

@Injectable({
  providedIn: 'root'
})

export class ApiService {

  private apiUrl = '/game/api';
  private gameDataSubject = new BehaviorSubject<GameResponse>({
    name: "",
    instructions: "",
    ingredients: [],
    glass: "",
    category: "",
    image: "",
    score: 0,
    attemptsLeft: 5,
    correct: false,
    gameOver: false
  })
  gameData$ = this.gameDataSubject.asObservable();
  hiscores: Array<Hiscore> = [];
  isGameStarted: boolean = false;
  showHiscores: boolean = false;

  constructor(private http: HttpClient) { }

  startGame() {
    this.http.get<GameResponse>(this.apiUrl + "/start").subscribe({
      next: response => {
        this.gameDataSubject.next(response);
        this.isGameStarted = true;
        this.showHiscores = false;
      }
    });
  }

  guess(guess: string) {
    this.http.post<GameResponse>(this.apiUrl + "/guess", guess).subscribe({
      next: response => {
        this.gameDataSubject.next(response);
      }
    });
  }

  skip() {
    this.http.get<GameResponse>(this.apiUrl + "/skip").subscribe({
      next: response => {
        this.gameDataSubject.next(response);
      }
    });
  }

  next() {
    this.http.get<GameResponse>(this.apiUrl + "/next").subscribe({
      next: response => {
        this.gameDataSubject.next(response);
      }
    });
  }

  submitHiscore(name: string, score: number) {
    const params = {name, score}
    this.http.post<Hiscore[]>(this.apiUrl + "/submit", params).subscribe({
      next: () => {
        this.isGameStarted = false
      }
    })
  }

  getHiscores() {
    this.http.get<Hiscore[]>(this.apiUrl + "/hiscores").subscribe({
      next: response => {
        this.hiscores = response;
      }
    });
  }
}
