package vttp2022.mp2.shop.server.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vttp2022.mp2.shop.server.models.Product;
import vttp2022.mp2.shop.server.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public Product addNewProduct(Product product) throws SQLException, IOException{ 
        return productRepo.save(product);
    }

    // public List<Product> getAllProducts() {
    //     return productRepo.findAll();
    // }

    public List<Product> getAllProducts(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 12);
        Page<Product> page = productRepo.findAll(pageable);
        return page.getContent();
    }

    public Product getProductDetailsById(Integer productId) throws SQLException, IOException {
        return productRepo.findById(productId);
    }

    public void deleteProductDetails(Integer productId) throws SQLException {
        productRepo.deleteById(productId);
    }

    public List<Product> getProductDetails (boolean isSingleProductCheckout, Integer productId) throws SQLException {
        if (isSingleProductCheckout) {
            // we are going to buy a single product

            List<Product> list = new ArrayList<>();
            Product product = productRepo.findById(productId);
            list.add(product);
            return list;
        } else {
            // we are going to checkout entire cart
        }

        return new ArrayList<>();
    }

}
