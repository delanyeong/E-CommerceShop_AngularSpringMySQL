package vttp2022.mp2.shop.server.models;

public class Cart {
    private Integer cartId;
    private Product product;
    private User user;

    public Cart() {}

    public Cart(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    public Integer getCartId() { return cartId; }
    public void setCartId(Integer cartId) { this.cartId = cartId;}

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product;}

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user;}


    
}
