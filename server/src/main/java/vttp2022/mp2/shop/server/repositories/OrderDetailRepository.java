package vttp2022.mp2.shop.server.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.mp2.shop.server.models.ImageModel;
import vttp2022.mp2.shop.server.models.OrderDetail;
import vttp2022.mp2.shop.server.models.Product;
import vttp2022.mp2.shop.server.models.Role;
import vttp2022.mp2.shop.server.models.User;

@Repository
public class OrderDetailRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

// public void save(OrderDetail orderDetail) {
//     String sql = "INSERT INTO order_detail (order_full_name, order_full_order, order_contact_number, " +
//                  "order_alternate_contact_number, order_status, order_amount, product_id, user_name) " +
//                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

//     jdbcTemplate.update(sql, orderDetail.getOrderFullName(), orderDetail.getOrderFullOrder(),
//                          orderDetail.getOrderContactNumber(), orderDetail.getOrderAlternateContactNumber(),
//                          orderDetail.getOrderStatus(), orderDetail.getOrderAmount(), orderDetail.getProduct().getProductId(),
//                          orderDetail.getUser().getUserName());
// }

public void save(OrderDetail orderDetail) {
    if (orderDetail.getOrderId() == null) {
        // Insert a new row
        String sql = "INSERT INTO order_detail (order_full_name, order_full_order, order_contact_number, " +
                 "order_alternate_contact_number, order_status, order_amount, product_id, user_name) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderDetail.getOrderFullName(), orderDetail.getOrderFullOrder(),
                         orderDetail.getOrderContactNumber(), orderDetail.getOrderAlternateContactNumber(),
                         orderDetail.getOrderStatus(), orderDetail.getOrderAmount(), orderDetail.getProduct().getProductId(),
                         orderDetail.getUser().getUserName());
    } else {
        // Update an existing row
        String sql = "UPDATE order_detail SET order_full_name=?, order_full_order=?, order_contact_number=?, " +
                 "order_alternate_contact_number=?, order_status=?, order_amount=?, product_id=?, user_name=? " +
                 "WHERE order_id=?";
        jdbcTemplate.update(sql, orderDetail.getOrderFullName(), orderDetail.getOrderFullOrder(),
                         orderDetail.getOrderContactNumber(), orderDetail.getOrderAlternateContactNumber(),
                         orderDetail.getOrderStatus(), orderDetail.getOrderAmount(), orderDetail.getProduct().getProductId(),
                         orderDetail.getUser().getUserName(), orderDetail.getOrderId());
    }
}


public List<OrderDetail> findByUser(User user) {
    String sql = "SELECT * FROM order_detail " +
            "JOIN product ON order_detail.product_id = product.product_id " +
            "JOIN user ON order_detail.user_name = user.user_name " +
            "JOIN user_role ON user.user_name = user_role.user_name " +
            "JOIN role ON user_role.role_name = role.role_name " +
            "JOIN product_images ON product.product_id = product_images.product_id " +
            "JOIN image_model ON product_images.image_id = image_model.id " +
            "WHERE user.user_name = ?";
    return jdbcTemplate.query(sql, ps -> ps.setString(1, user.getUserName()), rs -> {
        List<OrderDetail> orderDetails = new ArrayList<>();
        while (rs.next()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(rs.getInt("order_id"));
            orderDetail.setOrderFullName(rs.getString("order_full_name"));
            orderDetail.setOrderFullOrder(rs.getString("order_full_order"));
            orderDetail.setOrderContactNumber(rs.getString("order_contact_number"));
            orderDetail.setOrderAlternateContactNumber(rs.getString("order_alternate_contact_number"));
            orderDetail.setOrderStatus(rs.getString("order_status"));
            orderDetail.setOrderAmount(rs.getDouble("order_amount"));
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
            orderDetail.setProduct(product);
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
            orderDetail.setUser(u);
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    });
}

public List<OrderDetail> findAll() {
    String sql = "SELECT * FROM order_detail " +
            "JOIN product ON order_detail.product_id = product.product_id " +
            "JOIN user ON order_detail.user_name = user.user_name " +
            "JOIN user_role ON user.user_name = user_role.user_name " +
            "JOIN role ON user_role.role_name = role.role_name " +
            "JOIN product_images ON product.product_id = product_images.product_id " +
            "JOIN image_model ON product_images.image_id = image_model.id";
    return jdbcTemplate.query(sql, rs -> {
        Map<Integer, OrderDetail> orderDetailMap = new HashMap<>();
        while (rs.next()) {
            Integer orderId = rs.getInt("order_id");
            OrderDetail orderDetail = orderDetailMap.get(orderId);
            if (orderDetail == null) {
                orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderId);
                orderDetail.setOrderFullName(rs.getString("order_full_name"));
                orderDetail.setOrderFullOrder(rs.getString("order_full_order"));
                orderDetail.setOrderContactNumber(rs.getString("order_contact_number"));
                orderDetail.setOrderAlternateContactNumber(rs.getString("order_alternate_contact_number"));
                orderDetail.setOrderStatus(rs.getString("order_status"));
                orderDetail.setOrderAmount(rs.getDouble("order_amount"));
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
                orderDetail.setProduct(product);
                User user = new User();
                user.setUserName(rs.getString("user_name"));
                user.setUserFirstName(rs.getString("user_first_name"));
                user.setUserLastName(rs.getString("user_last_name"));
                user.setUserPassword(rs.getString("user_password"));
                Set<Role> roles = new HashSet<>();
                Role role = new Role();
                role.setRoleName(rs.getString("role_name"));
                role.setRoleDescription(rs.getString("role_description"));
                roles.add(role);
                user.setRole(roles);
                orderDetail.setUser(user);
                orderDetailMap.put(orderId, orderDetail);
            } else {
                ImageModel image = new ImageModel();
                image.setId(rs.getLong("id"));
                image.setName(rs.getString("name"));
                image.setType(rs.getString("type"));
                byte[] binaryData = (byte[]) rs.getObject("picByte");
                image.setPicByte(binaryData);
                orderDetail.getProduct().getProductImages().add(image);
            }
        }
        return new ArrayList<>(orderDetailMap.values());
    });
}

public OrderDetail findById(Integer orderId) {
    String sql = "SELECT * FROM order_detail " +
            "JOIN product ON order_detail.product_id = product.product_id " +
            "JOIN user ON order_detail.user_name = user.user_name " +
            "JOIN user_role ON user.user_name = user_role.user_name " +
            "JOIN role ON user_role.role_name = role.role_name " +
            "JOIN product_images ON product.product_id = product_images.product_id " +
            "JOIN image_model ON product_images.image_id = image_model.id " +
            "WHERE order_id = ?";
    return jdbcTemplate.query(sql, ps -> ps.setInt(1, orderId), rs -> {
        if (rs.next()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(rs.getInt("order_id"));
            orderDetail.setOrderFullName(rs.getString("order_full_name"));
            orderDetail.setOrderFullOrder(rs.getString("order_full_order"));
            orderDetail.setOrderContactNumber(rs.getString("order_contact_number"));
            orderDetail.setOrderAlternateContactNumber(rs.getString("order_alternate_contact_number"));
            orderDetail.setOrderStatus(rs.getString("order_status"));
            orderDetail.setOrderAmount(rs.getDouble("order_amount"));
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
            orderDetail.setProduct(product);
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
            orderDetail.setUser(u);
            return orderDetail;
        } else {
            return null;
        }
    });
}

public List<OrderDetail> findByOrderStatus(String status) {
    String sql = "SELECT * FROM order_detail " +
                 "JOIN product ON order_detail.product_id = product.product_id " +
                 "JOIN user ON order_detail.user_name = user.user_name " +
                 "JOIN user_role ON user.user_name = user_role.user_name " +
                 "JOIN role ON user_role.role_name = role.role_name " +
                 "JOIN product_images ON product.product_id = product_images.product_id " +
                 "JOIN image_model ON product_images.image_id = image_model.id " +
                 "WHERE order_detail.order_status = ?";

    return jdbcTemplate.query(sql, ps -> ps.setString(1, status), rs -> {
        List<OrderDetail> orderDetails = new ArrayList<>();
        while (rs.next()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(rs.getInt("order_id"));
            orderDetail.setOrderFullName(rs.getString("order_full_name"));
            orderDetail.setOrderFullOrder(rs.getString("order_full_order"));
            orderDetail.setOrderContactNumber(rs.getString("order_contact_number"));
            orderDetail.setOrderAlternateContactNumber(rs.getString("order_alternate_contact_number"));
            orderDetail.setOrderStatus(rs.getString("order_status"));
            orderDetail.setOrderAmount(rs.getDouble("order_amount"));
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
            orderDetail.setProduct(product);
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
            orderDetail.setUser(u);
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    });
}






}
