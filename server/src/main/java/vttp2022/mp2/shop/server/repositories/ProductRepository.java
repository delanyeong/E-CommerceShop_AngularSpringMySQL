package vttp2022.mp2.shop.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.mp2.shop.server.models.ImageModel;
import vttp2022.mp2.shop.server.models.Product;

import static vttp2022.mp2.shop.server.repositories.Queries.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class ProductRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // *** KEEP THIS WORKING VERSION ***
    // public Product save(Product product) throws SQLException {
    //     int rowsAffected = jdbcTemplate.update(SQL_ADD_NEW_PRODUCT, product.getProductName(), product.getProductDescription(), product.getProductDiscountedPrice(), product.getProductActualPrice());

    //     if (rowsAffected == 0) {
    //         throw new SQLException("Creating user failed, no rows affected.");
    //     }

    //     SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_PRODUCT_BY_NAME, product.getProductName());
    //     if (!rs.next())
	// 		// return Optional.empty();
    //         throw new SQLException("Creating role failed, no ID obtained.");
	// 	return Product.create(rs);
    // }

    // *** LATEST WORKING VERSION ***
    public Product save(Product product) throws SQLException, IOException {

        //Product details save part
        int rowsAffected = jdbcTemplate.update(SQL_ADD_NEW_PRODUCT, product.getProductName(), product.getProductDescription(), product.getProductDiscountedPrice(), product.getProductActualPrice());

        if (rowsAffected == 0) {
            throw new SQLException("Creating product failed, no rows affected.");
        }

        //Product image save part
        // Retrieve the product_id of the newly added product
        Long productId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        // Save each image to image_model table and add relation between product and image in product_images table
        for (ImageModel image : product.getProductImages()) {
            jdbcTemplate.update("INSERT INTO image_model (name, type, picByte) VALUES (?, ?, ?)", image.getName(), image.getType(), image.getPicByte());

            Long imageId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

            jdbcTemplate.update("INSERT INTO product_images (product_id, image_id) VALUES (?, ?)", productId, imageId);
        }

        // =========
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_PRODUCT_BY_NAME, product.getProductName());
        if (!rs.next())
			// return Optional.empty();
            throw new SQLException("Creating product failed, no ID obtained.");
		return Product.create(rs);
    }

    // public void saveProducts (List<Product> gifList) {

    //     jdbcTemplate.update(SQL_INIT_PRODUCTS_TABLE);

    //     gifList.stream()
    //         .forEach(v -> {
    //             jdbcTemplate.update(SQL_INIT_PRODUCTS, 
    //                 v.getId(), 
    //                 v.getName(), 
    //                 v.getCategory(), 
    //                 v.getPrice(), 
    //                 v.getImage(), 
    //                 v.getQuantity());
    //         });



    // }

}
