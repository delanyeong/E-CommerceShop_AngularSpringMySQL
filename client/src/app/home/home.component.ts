import { Component, OnInit } from '@angular/core';
import { ProductService } from '../_services/product.service';
import { Product } from '../_model/product.model';
import { map } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { ImageProcessingService } from '../image-processing.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  pageNumber: number = 0;
  productDetails: Product[] = [];

  showLoadButton = false;

  constructor(
    private productSvc: ProductService,
    private imageProcessingService: ImageProcessingService,
    private router: Router

    ) { }

  ngOnInit(): void {
    // this.getAllProducts();
    this.getAllProducts();
  }

  // public getAllProducts() {
  //   this.productSvc.getAllProducts()
  //   .pipe(
  //     map((x: Product[], i) => x.map((product: Product) => this.imageProcessingService.createImages(product)))
  //   )
  //   .subscribe(
  //     (resp: Product[]) => {
  //       console.log (resp);
  //       this.productDetails = resp;
  //     }, (error: HttpErrorResponse) => {
  //       console.log(error);
  //     }
  //   );
  // }

  public getAllProducts() {
    this.productSvc.getAllProducts(this.pageNumber)
    .pipe(
      map((x: Product[], i) => x.map((product: Product) => this.imageProcessingService.createImages(product)))
    )
    .subscribe(
      (resp: Product[]) => {
        console.log (resp);
        if(resp.length == 12) {
          this.showLoadButton = true;
        } else {
          this.showLoadButton = false;
        }
        resp.forEach(p => this.productDetails.push(p));
        // this.productDetails = resp;
      }, (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }

  showProductDetails(productId:any) {
    this.router.navigate(['/productViewDetails', {productId: productId}])
  }

  public loadMoreProduct() {
    this.pageNumber = this.pageNumber + 1;
    this.getAllProducts();
  }

}
