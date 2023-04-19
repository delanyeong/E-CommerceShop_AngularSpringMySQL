package vttp2022.mp2.shop.server.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vttp2022.mp2.shop.server.config.JwtRequestFilter;
import vttp2022.mp2.shop.server.models.Cart;
import vttp2022.mp2.shop.server.models.Product;
import vttp2022.mp2.shop.server.models.User;
import vttp2022.mp2.shop.server.repositories.CartRepository;
import vttp2022.mp2.shop.server.repositories.ProductRepository;
import vttp2022.mp2.shop.server.repositories.UserRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CartRepository cartRepo;

    public Product addNewProduct(Product product) throws SQLException, IOException{ 
        return productRepo.save(product);
    }

    // public List<Product> getAllProducts() {
    //     return productRepo.findAll();
    // }

    // public List<Product> getAllProducts(int pageNumber) {
    //     Pageable pageable = PageRequest.of(pageNumber, 12);
    //     Page<Product> page = productRepo.findAll(pageable);
    //     return page.getContent();
    // }

    public List<Product> getAllProducts(int pageNumber, String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber, 12);
        if(searchKey.equals("")) {
            Page<Product> page = productRepo.findAll(pageable);
            return page.getContent();
        } else {
            return productRepo.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey, searchKey, pageable).getContent();
        }
        
    }

    public Product getProductDetailsById(Integer productId) throws SQLException, IOException {
        return productRepo.findById(productId);
    }

    public void deleteProductDetails(Integer productId) throws SQLException {
        productRepo.deleteById(productId);
    }

    public List<Product> getProductDetails (boolean isSingleProductCheckout, Integer productId) throws SQLException {
        if (isSingleProductCheckout && productId != 0) {
            // we are going to buy a single product

            List<Product> list = new ArrayList<>();
            Product product = productRepo.findById(productId);
            list.add(product);
            return list;
        } else {
            // we are going to checkout entire cart
            String username = JwtRequestFilter.CURRENT_USER;
            User user = userRepo.findById(username).get();
            List<Cart> carts = cartRepo.findByUser(user);

            return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());
        }
    }

}
