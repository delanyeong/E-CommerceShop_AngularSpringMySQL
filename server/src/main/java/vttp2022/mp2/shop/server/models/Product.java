package vttp2022.mp2.shop.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Product {
    private Integer id;
    private String name;
    private String category;
    private Double price;
    private String image;
    private Integer quantity;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void setId(Integer id) { this.id = id; }
    public Integer getId() { return id; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public static Product create(JsonObject jo) {
        Product p = new Product();
        p.setId(jo.getInt("id"));
        p.setName(jo.getString("title"));
        p.setCategory(jo.getString("category"));
        p.setPrice(Double.valueOf(jo.getInt("price")));
        p.setImage(jo.getString("thumbnail"));
        p.setQuantity(0);
        
        return p;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("id", id)
            .add("name", name)
            .add("category", category)
            .add("price", price)
            .add("thumbnail", image)
            .add("quantity", quantity)
            .build();
    }


    
}
