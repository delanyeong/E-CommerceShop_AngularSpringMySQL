package vttp2022.mp2.shop.server.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vttp2022.mp2.shop.server.models.OrderInput;
import vttp2022.mp2.shop.server.services.OrderDetailService;
import vttp2022.mp2.shop.server.services.ProductService;

@RestController
public class OrderDetailController {
    
    @Autowired
    private OrderDetailService orderDetailService;


    @PreAuthorize("hasRole('User')")
    @PostMapping(path="/placeOrder")
    public void placeOrder(@RequestBody OrderInput orderInput) throws SQLException {
        orderDetailService.placeOrder(orderInput);
    }

}
