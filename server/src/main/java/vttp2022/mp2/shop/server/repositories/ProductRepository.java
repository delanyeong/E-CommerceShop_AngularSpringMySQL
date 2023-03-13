package vttp2022.mp2.shop.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.mp2.shop.server.models.Product;

import static vttp2022.mp2.shop.server.repositories.Queries.*;

import java.util.List;

@Repository
public class ProductRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveProducts (List<Product> gifList) {

        jdbcTemplate.update(SQL_INIT_PRODUCTS_TABLE);

        gifList.stream()
            .forEach(v -> {
                jdbcTemplate.update(SQL_INIT_PRODUCTS, 
                    v.getId(), 
                    v.getName(), 
                    v.getCategory(), 
                    v.getPrice(), 
                    v.getImage(), 
                    v.getQuantity());
            });



    }

}
