import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private userAuthService : UserAuthService, private router: Router, private userService: UserService) {}

  ngOnInit(): void {
    
  }

  public isLoggedIn() {
    return this.userAuthService.isLoggedIn()
  }

  //clear localStorage when logout
  public logout() {
    this.userAuthService.clear()
    this.router.navigate(['/'])
  }

  public usrSvcRoleMatch(allowedRoles:any) {
    return this.userService.roleMatch(allowedRoles)
  }

  public isAdmin() {
    return this.userAuthService.isAdmin();
  }

  public isUser() {
    return this.userAuthService.isUser();
    return this.userAuthService.isUser();
  }

}
