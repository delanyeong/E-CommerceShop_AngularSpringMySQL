import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  // loginForm!: FormGroup

  constructor(private userService: UserService, private userAuthService: UserAuthService, private router: Router) { }

  ngOnInit(): void {
    // Build the form
    // this.loginForm = this.createForm()
  }

  process(loginForm : NgForm) {
    this.userService.login(loginForm.value).subscribe(
      (response:any) => {
        this.userAuthService.setRoles(response.user.role)
        this.userAuthService.setToken(response.jwtToken)
        //redirect
        const role = response.user.role[0].roleName
        if (role == 'Admin') {
          this.router.navigate(['/admin'])
        } else {
          this.router.navigate(['/user'])
        }
      },
      (error) => {
        console.log(error);
      }
    )
  }

  // private createForm(): FormGroup {
  //   return this.fb.group({
  //     userName: this.fb.control<string>('', [ Validators.required, Validators.minLength(3) ]),
  //     userPassword: this.fb.control<string>('', [ Validators.required, Validators.email ])
  //   })
  // }

  

}
