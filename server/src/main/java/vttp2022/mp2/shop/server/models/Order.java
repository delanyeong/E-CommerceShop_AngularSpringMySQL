package vttp2022.mp2.shop.server.models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Order {
    private Integer orderId;
	private Integer uid;
	private Integer quantity;
	private String date;
    //
    // private String orderId;
	// private String deliveryId;
	private String name;
	private String address;
	private String email;
	// private String status;
	private Date orderDate = new Date();
	private List<Product> lineItems = new LinkedList<>();


    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public Integer getUid() { return uid; }
    public void setUid(Integer uid) { this.uid = uid; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public List<Product> getLineItems() { return lineItems; }
    public void setLineItems(List<Product> lineItems) { this.lineItems = lineItems; }

    

    
}
