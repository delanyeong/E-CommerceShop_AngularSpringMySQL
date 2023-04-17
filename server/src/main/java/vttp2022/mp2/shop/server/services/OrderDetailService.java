package vttp2022.mp2.shop.server.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.mp2.shop.server.config.JwtRequestFilter;
import vttp2022.mp2.shop.server.models.OrderDetail;
import vttp2022.mp2.shop.server.models.OrderInput;
import vttp2022.mp2.shop.server.models.OrderProductQuantity;
import vttp2022.mp2.shop.server.models.Product;
import vttp2022.mp2.shop.server.models.User;
import vttp2022.mp2.shop.server.repositories.OrderDetailRepository;
import vttp2022.mp2.shop.server.repositories.ProductRepository;
import vttp2022.mp2.shop.server.repositories.UserRepository;

@Service
public class OrderDetailService {

    private static final String ORDER_PLACED = "Placed";

    @Autowired
    private OrderDetailRepository orderDetailRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    public void placeOrder(OrderInput orderInput) throws SQLException {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o : productQuantityList) {
            Product product = productRepo.findById(o.getProductId());

            String currentUser = JwtRequestFilter.CURRENT_USER;
            User user = userRepo.findById(currentUser).get();

            OrderDetail orderDetail = new OrderDetail(
                orderInput.getFullName(),
                orderInput.getFullAddress(),
                orderInput.getContactNumber(),
                orderInput.getAlternateContactNumber(),
                ORDER_PLACED,
                product.getProductActualPrice() * o.getQuantity(),
                product,
                user
            );

            orderDetailRepo.save(orderDetail);
        }
    } 
    
}
