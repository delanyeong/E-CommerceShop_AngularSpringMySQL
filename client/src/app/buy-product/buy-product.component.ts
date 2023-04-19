import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { OrderDetails } from '../_model/order-details.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';
// import * as Razorpay from 'razorpay';

declare var Razorpay: any;

@Component({
  selector: 'app-buy-product',
  templateUrl: './buy-product.component.html',
  styleUrls: ['./buy-product.component.css']
})
export class BuyProductComponent implements OnInit {

  isSingleProductCheckout: string | null = '';
  productDetails: Product [] = [];

  orderDetails: OrderDetails = {
    fullName: '',
    fullAddress: '',
    contactNumber: '',
    alternateContactNumber: '',
    orderProductQuantityList: []
  }

  constructor (private activatedRoute: ActivatedRoute,
    private productSvc: ProductService,
    private router: Router) {}

  ngOnInit(): void {
    this.productDetails = this.activatedRoute.snapshot.data['productDetails'];
    this.isSingleProductCheckout = this.activatedRoute.snapshot.paramMap.get("isSingleProductCheckout");

    this.productDetails.forEach(
      x => this.orderDetails.orderProductQuantityList.push(
        {productId: x.productId, quantity:1}
      )
    )

    console.log(this.productDetails)
    console.log(this.orderDetails)
  }

  // public placeOrder (orderForm: NgForm) {
  //   this.productSvc.placeOrder(this.orderDetails).subscribe(
  //     (resp) => {
  //       console.log(resp);
  //       orderForm.reset();
  //       this.router.navigate(["/orderConfirm"])
  //     },
  //     (err) => {
  //       console.log(err);
  //     }
  //   )
  // }

  public placeOrder (orderForm: NgForm) {
    this.productSvc.placeOrder(this.orderDetails, this.isSingleProductCheckout).subscribe(
      (resp) => {
        console.log(resp);
        orderForm.reset();
        this.router.navigate(["/orderConfirm"])
      },
      (err) => {
        console.log(err);
      }
    )
  }

  getQuantityForProduct(productId:any) {
    const filterProduct = this.orderDetails.orderProductQuantityList.filter(
      (productQuantity) => productQuantity.productId === productId
    )

    return filterProduct[0].quantity
  }

  getCalculatedTotal(productId:any, productDiscountedPrice:any) {
    const filteredProduct = this.orderDetails.orderProductQuantityList.filter(
      (productQuantity) => productQuantity.productId === productId
    )

      return filteredProduct[0].quantity * productDiscountedPrice
  }

  onQuantityChanged(q:any, productId:any) {
    this.orderDetails.orderProductQuantityList.filter(
      (orderProduct) => orderProduct.productId === productId
    )[0].quantity = q;

  }

  getCalculatedGrandTotal() {
    let grandTotal = 0;
    this.orderDetails.orderProductQuantityList.forEach(
      (productQuantity) => {
        const price = this.productDetails.filter(product => product.productId === productQuantity.productId)[0].productDiscountedPrice 
        grandTotal = grandTotal + price * productQuantity.quantity
      }
    )

    return grandTotal
  }

  createTransactionAndPlaceOrder(orderForm: NgForm) {
    let amount = this.getCalculatedGrandTotal();
    this.productSvc.createTransaction(amount).subscribe(
      (response) => {
        console.log(response);
        this.openTransactionModal(response, orderForm);
      }, (error) => {
        console.log(error)
      }
    )

  }

  openTransactionModal(response: any, orderForm: NgForm) {
    var options = {
      order_id: response.orderId,
      key: response.key,
      amount: response.amount,
      currency: response.currency,
      name: 'VTTP',
      description: 'Payment of online shopping',
      image: 'https://cdn.pixabay.com/photo/2023/04/15/08/52/butterfly-7927279_640.jpg',
      handler: (response: any) => {
        if(response != null && response.razorpay_payment_id != null) {
          this.processResponse(response, orderForm)
        } else {
          alert("Payment failed")
        }
      },
      prefill: {
        name: 'VTTP',
        email: 'VTTP@GMAIL.COM',
        contact: '1234567890'
      },
      notes: {
        address: 'Online Shopping'
      },
      theme: {
        color: '#F37254'
      }

    }

    var razorPayObject = new Razorpay(options);
    razorPayObject.open();
  }
   
  processResponse(resp:any, orderForm: NgForm) {
    this.placeOrder(orderForm)
  }



}
