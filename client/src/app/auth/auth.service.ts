import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { User } from './user';

@Injectable()
export class AuthService {
  private loggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  get isLoggedIn() {
    return this.loggedIn.asObservable();
  }

  constructor(
    private router: Router,
    private http: HttpClient
  ) {}

  login(user: User) {
    const credentials = {username: user.userName, password: user.password};
    console.log('attempAuth ::');
    this.http.post<any>(`http://localhost:9800/api/user/authenticate`,credentials)
    .subscribe(data => {
        // login successful if there's a jwt token in the response
        if (data && data.userName !== '') {
          this.loggedIn.next(true);
          this.router.navigate(['/']);
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            localStorage.setItem('currentUser', JSON.stringify(data.username));
        }
    });
  }

  logout() {
    this.loggedIn.next(false);
    this.router.navigate(['/login']);
  }
}
