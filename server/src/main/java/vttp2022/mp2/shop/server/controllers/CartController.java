package vttp2022.mp2.shop.server.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import vttp2022.mp2.shop.server.models.Cart;
import vttp2022.mp2.shop.server.services.CartService;

@RestController
public class CartController {

    @Autowired
    private CartService cartSvc;
    
    @PreAuthorize("hasRole('User')")
    @GetMapping(path="/addToCart/{productId}")
    public Cart addToCart(@PathVariable(name="productId") Integer productId) throws SQLException {
        return cartSvc.addToCart(productId);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping(path="/getCartDetails")
    public List<Cart> getCartDetails() {
        return cartSvc.getCartDetails();
    }
}
