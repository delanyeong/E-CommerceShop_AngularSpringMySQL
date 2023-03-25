import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserAuthService {

  constructor() { }
  
  public setRoles(roles:[]) {
    localStorage.setItem("roles", JSON.stringify(roles))
  }

  public getRoles(): [] {
    return JSON.parse(localStorage.getItem("roles")!)
    // https://stackoverflow.com/questions/46915002/argument-of-type-string-null-is-not-assignable-to-parameter-of-type-string
  }

  public setToken(jwtToken: string) {
    localStorage.setItem("jwtToken", jwtToken);
  }

  public getToken(): string | null {
    return localStorage.getItem('jwtToken')
  }

  public clear() {
    localStorage.clear()
  }

  public isLoggedIn() {
    return this.getRoles() && this.getToken()
  }
}
