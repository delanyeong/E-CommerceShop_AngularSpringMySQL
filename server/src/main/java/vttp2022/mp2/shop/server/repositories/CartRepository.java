package vttp2022.mp2.shop.server.repositories;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import vttp2022.mp2.shop.server.models.Cart;
import vttp2022.mp2.shop.server.models.ImageModel;
import vttp2022.mp2.shop.server.models.Product;
import vttp2022.mp2.shop.server.models.Role;
import vttp2022.mp2.shop.server.models.User;

@Repository
public class CartRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // public Cart save(Cart cart) {
    //     String sql = "INSERT INTO cart (product_id, user_name) VALUES (?, ?)";

    //     int rows = jdbcTemplate.update(sql, cart.getProduct().getProductId(), cart.getUser().getUserName());

    //     if (rows == 0) {
    //         return null;
    //     }


    //     return cart;
    // }

    public Cart save(Cart cart) {
        String sql = "INSERT INTO cart (product_id, user_name) VALUES (?, ?)";
    
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"cart_id"});
            ps.setInt(1, cart.getProduct().getProductId());
            ps.setString(2, cart.getUser().getUserName());
            return ps;
        }, keyHolder);
    
        if (rows == 0) {
            return null;
        }
    
        cart.setCartId(keyHolder.getKey().intValue());
        return cart;
    }

    // public List<Cart> findByUser(User user) {
    //     String sql = "SELECT * FROM cart " +
    //                  "JOIN product ON cart.product_id = product.product_id " +
    //                  "JOIN user ON cart.user_name = user.user_name " +
    //                  "WHERE user.user_name = ?";
    
    //     return jdbcTemplate.query(sql, ps -> ps.setString(1, user.getUserName()), rs -> {
    //         List<Cart> carts = new ArrayList<>();
    //         while (rs.next()) {
    //             Cart cart = new Cart();
    //             cart.setCartId(rs.getInt("cart_id"));
    
    //             Product product = new Product();
    //             product.setProductId(rs.getInt("product_id"));
    //             product.setProductName(rs.getString("product_name"));
    //             product.setProductDescription(rs.getString("product_description"));
    //             product.setProductDiscountedPrice(rs.getDouble("product_discounted_price"));
    //             product.setProductActualPrice(rs.getDouble("product_actual_price"));
    
    //             Set<ImageModel> images = new HashSet<>();
    //             ImageModel image = new ImageModel();
    //             image.setId(rs.getLong("id"));
    //             image.setName(rs.getString("name"));
    //             image.setType(rs.getString("type"));
    
    //             byte[] binaryData = new byte[rs.getMetaData().getColumnDisplaySize(1)]; // 1 is the column index
    //             for (int i = 0; i < binaryData.length; i++) {
    //                 binaryData[i] = rs.getByte("picByte");
    //             }
    
    //             image.setPicByte(binaryData);
    //             images.add(image);
    //             product.setProductImages(images);
    //             cart.setProduct(product);
    
    //             User u = new User();
    //             u.setUserName(rs.getString("user_name"));
    //             u.setUserFirstName(rs.getString("user_first_name"));
    //             u.setUserLastName(rs.getString("user_last_name"));
    //             u.setUserPassword(rs.getString("user_password"));
    
    //             Set<Role> roles = new HashSet<>();
    //             Role role = new Role();
    //             role.setRoleName(rs.getString("role_name"));
    //             role.setRoleDescription(rs.getString("role_description"));
    //             roles.add(role);
    //             u.setRole(roles);
    
    //             cart.setUser(u);
    //             carts.add(cart);
    //         }
    //         return carts;
    //     });
    // }

    // public List<Cart> findByUser(User user) {
    //     String sql = "SELECT * FROM cart " +
    //                  "JOIN product ON cart.product_id = product.product_id " +
    //                  "JOIN user ON cart.user_name = user.user_name " +
    //                  "JOIN product_images ON product.product_id = product_images.product_id " +
    //                  "JOIN image_model ON product_images.image_id = image_model.id " +
    //                  "WHERE user.user_name = ?";
    //     return jdbcTemplate.query(sql, ps -> ps.setString(1, user.getUserName()), rs -> {
    //         List<Cart> carts = new ArrayList<>();
    //         while (rs.next()) {
    //             Cart cart = new Cart();
    //             cart.setCartId(rs.getInt("cart_id"));
    //             Product product = new Product();
    //             product.setProductId(rs.getInt("product_id"));
    //             product.setProductName(rs.getString("product_name"));
    //             product.setProductDescription(rs.getString("product_description"));
    //             product.setProductDiscountedPrice(rs.getDouble("product_discounted_price"));
    //             product.setProductActualPrice(rs.getDouble("product_actual_price"));
    //             Set<ImageModel> images = new HashSet<>();
    //             ImageModel image = new ImageModel();
    //             image.setId(rs.getLong("id"));
    //             image.setName(rs.getString("name"));
    //             image.setType(rs.getString("type"));
    //             byte[] binaryData = (byte[]) rs.getObject("picByte");
    //             image.setPicByte(binaryData);
    //             images.add(image);
    //             product.setProductImages(images);
    //             cart.setProduct(product);
    //             User u = new User();
    //             u.setUserName(rs.getString("user_name"));
    //             u.setUserFirstName(rs.getString("user_first_name"));
    //             u.setUserLastName(rs.getString("user_last_name"));
    //             u.setUserPassword(rs.getString("user_password"));
    //             Set<Role> roles = new HashSet<>();
    //             Role role = new Role();
    //             role.setRoleName(rs.getString("role_name"));
    //             role.setRoleDescription(rs.getString("role_description"));
    //             roles.add(role);
    //             u.setRole(roles);
    //             cart.setUser(u);
    //             carts.add(cart);
    //         }
    //         return carts;
    //     });
    // }

    public List<Cart> findByUser(User user) {
        String sql = "SELECT * FROM cart " +
                     "JOIN product ON cart.product_id = product.product_id " +
                     "JOIN user ON cart.user_name = user.user_name " +
                     "JOIN user_role ON user.user_name = user_role.user_name " +
                     "JOIN role ON user_role.role_name = role.role_name " +
                     "JOIN product_images ON product.product_id = product_images.product_id " +
                     "JOIN image_model ON product_images.image_id = image_model.id " +
                     "WHERE user.user_name = ?";
        return jdbcTemplate.query(sql, ps -> ps.setString(1, user.getUserName()), rs -> {
            List<Cart> carts = new ArrayList<>();
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setCartId(rs.getInt("cart_id"));
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setProductDescription(rs.getString("product_description"));
                product.setProductDiscountedPrice(rs.getDouble("product_discounted_price"));
                product.setProductActualPrice(rs.getDouble("product_actual_price"));
                Set<ImageModel> images = new HashSet<>();
                ImageModel image = new ImageModel();
                image.setId(rs.getLong("id"));
                image.setName(rs.getString("name"));
                image.setType(rs.getString("type"));
                byte[] binaryData = (byte[]) rs.getObject("picByte");
                image.setPicByte(binaryData);
                images.add(image);
                product.setProductImages(images);
                cart.setProduct(product);
                User u = new User();
                u.setUserName(rs.getString("user_name"));
                u.setUserFirstName(rs.getString("user_first_name"));
                u.setUserLastName(rs.getString("user_last_name"));
                u.setUserPassword(rs.getString("user_password"));
                Set<Role> roles = new HashSet<>();
                Role role = new Role();
                role.setRoleName(rs.getString("role_name"));
                role.setRoleDescription(rs.getString("role_description"));
                roles.add(role);
                u.setRole(roles);
                cart.setUser(u);
                carts.add(cart);
            }
            return carts;
        });
    }

    public void deleteById(int cartId) {
        String sql = "DELETE FROM cart WHERE cart_id = ?";
        jdbcTemplate.update(sql, cartId);
    }
    
    

//     CREATE TABLE `user` (
//   `user_name` varchar(255) NOT NULL,
//   `user_first_name` varchar(255) DEFAULT NULL,
//   `user_last_name` varchar(255) DEFAULT NULL,
//   `user_password` varchar(255) DEFAULT NULL,
//   PRIMARY KEY (`user_name`)

//   CREATE TABLE `role` (
//   `role_name` varchar(255) NOT NULL,
//   `role_description` varchar(255) DEFAULT NULL,
//   PRIMARY KEY (`role_name`)
    
    
    
    
    
    

    
    
    
}
