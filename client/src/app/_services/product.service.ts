import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../_model/product.model';
import { OrderDetails } from '../_model/order-details.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  public addProduct(product: FormData) {
    return this.http.post<Product>("http://localhost:8080/addNewProduct", product);
  }

  // public getAllProducts () {
  //   return this. http.get<Product[]>("http://localhost:8080/getAllProducts");
  // }

  public getAllProducts (pageNumber:any) {
    return this. http.get<Product[]>("http://localhost:8080/getAllProducts?pageNumber=" + pageNumber);
  }

  public getProductDetailsById(productId:any) {
    return this.http.get<Product>("http://localhost:8080/getProductDetailsById/" + productId);
  }

  public deleteProduct(productId: number) {
    return this.http.delete("http://localhost:8080/deleteProductDetails/" + productId);
  }

  public getProductDetails(isSingleProductCheckout:any, productId:any) {
    return this.http.get<Product[]>("http://localhost:8080/getProductDetails/" + isSingleProductCheckout + "/" + productId);
  }

  public placeOrder(orderDetails: OrderDetails) {
    return this.http.post("http://localhost:8080/placeOrder", orderDetails)
  }
}


