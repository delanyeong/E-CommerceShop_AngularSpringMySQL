import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../_model/product.model';
import { OrderDetails } from '../_model/order-details.model';
import { MyOrderDetails } from '../_model/order.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  public markAsDelivered(orderId:any) {
    return this.http.get("http://localhost:8080/markOrderAsDelivered/" + orderId)
  }

  public getAllOrderDetailsForAdmin() : Observable<MyOrderDetails []> {
    return this.http.get<MyOrderDetails[]>("http://localhost:8080/getAllOrderDetails");
  }

  public getMyOrders() : Observable<MyOrderDetails []> {
    return this.http.get<MyOrderDetails[]>("http://localhost:8080/getOrderDetails");
  }

  public deleteCartItem(cartId:any) {
    return this.http.delete("http://localhost:8080/deleteCartItem/" + cartId)
  }

  public addProduct(product: FormData) {
    return this.http.post<Product>("http://localhost:8080/addNewProduct", product);
  }

  // public getAllProducts () {
  //   return this.http.get<Product[]>("http://localhost:8080/getAllProducts");
  // }

  // public getAllProducts (pageNumber:any) {
  //   return this.http.get<Product[]>("http://localhost:8080/getAllProducts?pageNumber=" + pageNumber);
  // }

  public getAllProducts (pageNumber:any, searchkeyword: string = "") {
    return this.http.get<Product[]>("http://localhost:8080/getAllProducts?pageNumber=" + pageNumber + "&searchKey=" + searchkeyword);
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

  // public placeOrder(orderDetails: OrderDetails) {
  //   return this.http.post("http://localhost:8080/placeOrder", orderDetails)
  // }

  public placeOrder(orderDetails: OrderDetails, isCartCheckout:any) {
    return this.http.post("http://localhost:8080/placeOrder/"+ isCartCheckout, orderDetails)
  }

  public addToCart(productId:any) {
    return this.http.get("http://localhost:8080/addToCart/" + productId);
  }

  public getCartDetails() {
    return this.http.get("http://localhost:8080/getCartDetails");
  }
}


