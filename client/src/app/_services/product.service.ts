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

  public createTransaction(amount:any) {
    return this.http.get("/createTransaction/" + amount)
  }

  public markAsDelivered(orderId:any) {
    return this.http.get("/markOrderAsDelivered/" + orderId)
  }

  public getAllOrderDetailsForAdmin(status: string) : Observable<MyOrderDetails []> {
    return this.http.get<MyOrderDetails[]>("/getAllOrderDetails/" + status);
  }

  public getMyOrders() : Observable<MyOrderDetails []> {
    return this.http.get<MyOrderDetails[]>("/getOrderDetails");
  }

  public deleteCartItem(cartId:any) {
    return this.http.delete("/deleteCartItem/" + cartId)
  }

  public addProduct(product: FormData) {
    return this.http.post<Product>("/addNewProduct", product);
  }

  public getAllProducts (pageNumber:any, searchkeyword: string = "") {
    return this.http.get<Product[]>("/getAllProducts?pageNumber=" + pageNumber + "&searchKey=" + searchkeyword);
  }

  public getProductDetailsById(productId:any) {
    return this.http.get<Product>("/getProductDetailsById/" + productId);
  }

  public deleteProduct(productId: number) {
    return this.http.delete("/deleteProductDetails/" + productId);
  }

  public getProductDetails(isSingleProductCheckout:any, productId:any) {
    return this.http.get<Product[]>("/getProductDetails/" + isSingleProductCheckout + "/" + productId);
  }

  public placeOrder(orderDetails: OrderDetails, isCartCheckout:any) {
    return this.http.post("/placeOrder/"+ isCartCheckout, orderDetails)
  }

  public addToCart(productId:any) {
    return this.http.get("/addToCart/" + productId);
  }

  public getCartDetails() {
    return this.http.get("/getCartDetails");
  }
}


