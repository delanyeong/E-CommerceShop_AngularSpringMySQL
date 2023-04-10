package vttp2022.mp2.shop.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.mp2.shop.server.models.ImageModel;
import vttp2022.mp2.shop.server.models.Product;

import static vttp2022.mp2.shop.server.repositories.Queries.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;



    // METHOD: SAVE PRODUCT
    /* 
    *** KEEP THIS WORKING VERSION ***
        public Product save(Product product) throws SQLException {
            int rowsAffected = jdbcTemplate.update(SQL_ADD_NEW_PRODUCT, product.getProductName(), product.getProductDescription(), product.getProductDiscountedPrice(), product.getProductActualPrice());

            if (rowsAffected == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_PRODUCT_BY_NAME, product.getProductName());
            if (!rs.next())
                // return Optional.empty();
                throw new SQLException("Creating role failed, no ID obtained.");
            return Product.create(rs);
        }
    */
    

    // *** LATEST WORKING VERSION (START) ***
    public Product save(Product product) throws SQLException, IOException {

        //Product details save part
        int rowsAffected = jdbcTemplate.update(SQL_ADD_NEW_PRODUCT, product.getProductName(), product.getProductDescription(), product.getProductDiscountedPrice(), product.getProductActualPrice());

        if (rowsAffected == 0) {
            throw new SQLException("Creating product failed, no rows affected.");
        }

        //Product image save part
        // Retrieve the product_id of the newly added product
        // gives invalid column name error but can ignore
        Long productId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        // Save each image to image_model table and add relation between product and image in product_images table
        for (ImageModel image : product.getProductImages()) {
            jdbcTemplate.update("INSERT INTO image_model (name, type, picByte) VALUES (?, ?, ?)", image.getName(), image.getType(), image.getPicByte());

            // gives invalid column name error but can ignore
            Long imageId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

            jdbcTemplate.update("INSERT INTO product_images (product_id, image_id) VALUES (?, ?)", productId, imageId);
        }

        //
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_PRODUCT_BY_NAME, product.getProductName());
        if (!rs.next())
			// return Optional.empty();
            throw new SQLException("Creating product failed, no ID obtained.");
		return Product.create(rs);
    }
    // *** LATEST WORKING VERSION (END) ***



    // METHOD: FIND ALL PRODUCTS
    public List<Product> findAll() {
        String sql = "SELECT p.product_id, p.product_name, p.product_description, p.product_discounted_price, " +
        "p.product_actual_price, i.id, i.name, i.type, i.picByte " +
        "FROM product p " +
        "LEFT JOIN product_images pi ON p.product_id = pi.product_id " +
        "LEFT JOIN image_model i ON pi.image_id = i.id";
        
        List<Product> products = jdbcTemplate.query(sql, new ResultSetExtractor<List<Product>>() {
            @Override
            public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                Map<Integer, Product> productMap = new HashMap<>();
                
                while (rs.next()) {
                    Integer productId = rs.getInt("product_id");
                    Product product = productMap.get(productId);
                    
                    if (product == null) {
                        product = new Product();
                        product.setProductId(productId);
                        product.setProductName(rs.getString("product_name"));
                        product.setProductDescription(rs.getString("product_description"));
                        product.setProductDiscountedPrice(rs.getDouble("product_discounted_price"));
                        product.setProductActualPrice(rs.getDouble("product_actual_price"));
                        
                        product.setProductImages(new HashSet<>());
                        
                        productMap.put(productId, product);
                    }
                    
                    ImageModel image = new ImageModel();
                    image.setId(rs.getLong("id"));
                    image.setName(rs.getString("name"));
                    image.setType(rs.getString("type"));
                    image.setPicByte(rs.getBytes("picByte"));
                    
                    product.getProductImages().add(image);
                }
                
                return new ArrayList<>(productMap.values());
            }
        });
        
        return products;
    }
    
    /*  *** Simple way only (Not in use) ***
        public List<Product> findAll() {
            return jdbcTemplate.query(SQL_FIND_ALL_PRODUCTS, BeanPropertyRowMapper.newInstance(Product.class));
        }
    */
    
    
    
    // METHOD: NOT IN USE
    /*  *** GIPHY template try only (Not in use) ***

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
    */
    

}
