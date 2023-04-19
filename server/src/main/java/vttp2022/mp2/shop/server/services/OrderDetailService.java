package vttp2022.mp2.shop.server.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.mp2.shop.server.config.JwtRequestFilter;
import vttp2022.mp2.shop.server.models.Cart;
import vttp2022.mp2.shop.server.models.OrderDetail;
import vttp2022.mp2.shop.server.models.OrderInput;
import vttp2022.mp2.shop.server.models.OrderProductQuantity;
import vttp2022.mp2.shop.server.models.Product;
import vttp2022.mp2.shop.server.models.User;
import vttp2022.mp2.shop.server.repositories.CartRepository;
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

    @Autowired
    private CartRepository cartRepo;

    // public List<OrderDetail> getAllOrderDetails() {
    //     List<OrderDetail> orderDetails = new ArrayList<>();
    //     orderDetailRepo.findAll().forEach( 
    //         x -> orderDetails.add(x)
    //     );
    //     return orderDetails;
    // }

    public List<OrderDetail> getAllOrderDetails(String status) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        if(status.equals("All")) {
            orderDetailRepo.findAll().forEach( 
            x -> orderDetails.add(x)
            );
        } else {
            orderDetailRepo.findByOrderStatus(status).forEach(
                x -> orderDetails.add(x)
            );
        }
        
        return orderDetails;
    }

    public List<OrderDetail> getOrderDetails() {
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userRepo.findById(currentUser).get();

        return orderDetailRepo.findByUser(user);
    }

    // public void placeOrder(OrderInput orderInput) throws SQLException {
    //     List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

    //     for (OrderProductQuantity o : productQuantityList) {
    //         Product product = productRepo.findById(o.getProductId());

    //         String currentUser = JwtRequestFilter.CURRENT_USER;
    //         User user = userRepo.findById(currentUser).get();

    //         OrderDetail orderDetail = new OrderDetail(
    //             orderInput.getFullName(),
    //             orderInput.getFullAddress(),
    //             orderInput.getContactNumber(),
    //             orderInput.getAlternateContactNumber(),
    //             ORDER_PLACED,
    //             product.getProductDiscountedPrice() * o.getQuantity(),
    //             product,
    //             user
    //         );


    //         //empty the cart

    //         orderDetailRepo.save(orderDetail);
    //     }
    // } 

    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) throws SQLException {
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
                product.getProductDiscountedPrice() * o.getQuantity(),
                product,
                user
            );


            //empty the cart
            if(!isSingleProductCheckout) {
                List<Cart> carts = cartRepo.findByUser(user);
                carts.stream().forEach(x -> cartRepo.deleteById(x.getCartId()));
            }

            orderDetailRepo.save(orderDetail);
        }
    } 

    public void markOrderAsDelivered(Integer orderId) {
        OrderDetail orderDetail = orderDetailRepo.findById(orderId);

        if(orderDetail != null) {
            orderDetail.setOrderStatus("Delivered");
            orderDetailRepo.save(orderDetail);
        }
    }
    
}
