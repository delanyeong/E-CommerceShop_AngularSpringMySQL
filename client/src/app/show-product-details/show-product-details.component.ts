import { Component, OnInit } from '@angular/core';
import { ProductService } from '../_services/product.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Product } from '../_model/product.model';
import { MatDialog } from '@angular/material/dialog';
import { ShowProductImagesDialogComponent } from '../show-product-images-dialog/show-product-images-dialog.component';

@Component({
  selector: 'app-show-product-details',
  templateUrl: './show-product-details.component.html',
  styleUrls: ['./show-product-details.component.css']
})
export class ShowProductDetailsComponent implements OnInit {

  productDetails: Product[] = [];
  displayedColumns: string[] = ['Id', 'Product Name', 'Product Description', 'Product Discounted Price', 'Product Actual Price', 'Images', 'Edit', 'Delete'];

  constructor (private productService: ProductService, public imagesDialog: MatDialog) {}
  
  ngOnInit(): void {
    this.getAllProducts();
  }

  public getAllProducts() {
    this.productService.getAllProducts().subscribe(
      (resp: Product[]) => {
        console.log (resp);
        this.productDetails = resp;
      }, (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }

  deleteProduct(productId: number) {
    this.productService.deleteProduct(productId).subscribe(
      (resp) => {
        this.getAllProducts();
      },
      (error:HttpErrorResponse) => {
        console.log(error)
      }
    )
  }

  showImages(product: Product) {
    console.log(product);
    this.imagesDialog.open(ShowProductImagesDialogComponent, {
      height: '500px',
      width: '800px'

    });
  }

}
