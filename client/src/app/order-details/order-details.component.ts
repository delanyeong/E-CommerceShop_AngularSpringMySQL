import { Component, OnInit } from '@angular/core';
import { ProductService } from '../_services/product.service';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

  dataSource = [];
  displayedColumns: string[] = ['Id', 'Product Name', 'Name', 'Address', 'Contact No.', 'Status', 'Action'];

  status: string = "All";

  constructor(private productService: ProductService) {

  }

  ngOnInit(): void {
    this.getAllOrderDetailsForAdmin(this.status);
  }

  getAllOrderDetailsForAdmin(statusParameter: string) {
    this.productService.getAllOrderDetailsForAdmin(statusParameter).subscribe(
      (resp:any) => {
        this.dataSource = resp;
        console.log(resp);
      }, (error) => {
        console.log(error);
      }
    )
  }

  markAsDelivered(orderId:any) {
    console.log(orderId)
    this.productService.markAsDelivered(orderId).subscribe(
      (response) => {
        this.getAllOrderDetailsForAdmin(this.status);
        console.log(response);
      }, (error) => {
        console.log(error)
      }
    )
  }

}
