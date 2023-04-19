package vttp2022.mp2.shop.server.services;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.mp2.shop.server.config.JwtRequestFilter;
import vttp2022.mp2.shop.server.models.Cart;
import vttp2022.mp2.shop.server.models.Product;
import vttp2022.mp2.shop.server.models.User;
import vttp2022.mp2.shop.server.repositories.CartRepository;
import vttp2022.mp2.shop.server.repositories.ProductRepository;
import vttp2022.mp2.shop.server.repositories.UserRepository;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    public void deleteCartItem(Integer cartId) {
        cartRepo.deleteById(cartId);
    }


    public Cart addToCart(Integer productId) throws SQLException {
        Product product = productRepo.findById(productId);
        String username = JwtRequestFilter.CURRENT_USER;

        User user = null;
        if (username != null) {
            user = userRepo.findById(username).get();
        }

        List<Cart> cartList = cartRepo.findByUser(user);
        List<Cart> filteredList = cartList.stream().filter(x -> x.getProduct().getProductId() == productId).collect(Collectors.toList());

        if(filteredList.size() > 0) {
            return null;
        }

        if (product != null && user != null) {
            Cart cart = new Cart(product, user);
            return cartRepo.save(cart);
        }

        return null;
    }

    public List<Cart> getCartDetails() {
        String username = JwtRequestFilter.CURRENT_USER;
        User user = userRepo.findById(username).get();
        return cartRepo.findByUser(user);
    }
}
