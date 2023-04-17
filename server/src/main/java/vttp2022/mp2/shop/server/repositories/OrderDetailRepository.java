package vttp2022.mp2.shop.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.mp2.shop.server.models.OrderDetail;

@Repository
public class OrderDetailRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

public void save(OrderDetail orderDetail) {
    String sql = "INSERT INTO order_detail (order_full_name, order_full_order, order_contact_number, " +
                 "order_alternate_contact_number, order_status, order_amount, product_id, user_name) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    jdbcTemplate.update(sql, orderDetail.getOrderFullName(), orderDetail.getOrderFullOrder(),
                         orderDetail.getOrderContactNumber(), orderDetail.getOrderAlternateContactNumber(),
                         orderDetail.getOrderStatus(), orderDetail.getOrderAmount(), orderDetail.getProduct().getProductId(),
                         orderDetail.getUser().getUserName());
}


}
