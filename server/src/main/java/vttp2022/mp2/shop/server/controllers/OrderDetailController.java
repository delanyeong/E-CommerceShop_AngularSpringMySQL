package vttp2022.mp2.shop.server.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vttp2022.mp2.shop.server.models.OrderDetail;
import vttp2022.mp2.shop.server.models.OrderInput;
import vttp2022.mp2.shop.server.models.TransactionDetails;
// import vttp2022.mp2.shop.server.services.GMailer;
import vttp2022.mp2.shop.server.services.MailService;
import vttp2022.mp2.shop.server.services.OrderDetailService;
import vttp2022.mp2.shop.server.services.ProductService;

@RestController
public class OrderDetailController {
    
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private MailService mailSvc;


    // @PreAuthorize("hasRole('User')")
    // @PostMapping(path="/placeOrder")
    // public void placeOrder(@RequestBody OrderInput orderInput) throws SQLException {
    //     orderDetailService.placeOrder(orderInput);
    // }

    @PreAuthorize("hasRole('User')")
    @PostMapping(path="/placeOrder/{isSingleProductCheckout}")
    public void placeOrder(@PathVariable(name="isSingleProductCheckout") boolean isSingleProductCheckout, @RequestBody OrderInput orderInput) throws SQLException {
        orderDetailService.placeOrder(orderInput, isSingleProductCheckout);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping(path="/getOrderDetails")
    public List<OrderDetail> getOrderDetails() {
        return orderDetailService.getOrderDetails();
    }

    // @PreAuthorize("hasRole('Admin')")
    // @GetMapping(path="/getAllOrderDetails")
    // public List<OrderDetail> getAllOrderDetails() {
    //     return orderDetailService.getAllOrderDetails();
    // }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(path="/getAllOrderDetails/{status}")
    public List<OrderDetail> getAllOrderDetails(@PathVariable(name="status") String status) {
        return orderDetailService.getAllOrderDetails(status);
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(path="/markOrderAsDelivered/{orderId}")
    public void markOrderAsDelivered(@PathVariable(name="orderId") Integer orderId) {
        orderDetailService.markOrderAsDelivered(orderId);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping(path="/createTransaction/{amount}")
    public TransactionDetails createTransaction(@PathVariable (name = "amount") Double amount) throws Exception {
        mailSvc.sendMail("codesignalrocks@gmail.com", "Order Confirmed", "Order Confirmed");
        return orderDetailService.createTransaction(amount);
    }

}
