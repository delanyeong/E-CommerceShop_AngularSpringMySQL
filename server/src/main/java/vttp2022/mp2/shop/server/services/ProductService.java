package vttp2022.mp2.shop.server.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public void deleteProductDetails(Integer productId) throws SQLException {
        productRepo.deleteById(productId);
    }

}
