package vttp2022.mp2.shop.server.models;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Product {


    private Integer productId;
    private String productName;
    private String productDescription;
    private Double productDiscountedPrice;
    private Double productActualPrice;

    private Set<ImageModel> productImages;

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }
    
    public Double getProductDiscountedPrice() { return productDiscountedPrice; }
    public void setProductDiscountedPrice(Double productDiscountedPrice) { this.productDiscountedPrice =  productDiscountedPrice; }
    
    public Double getProductActualPrice() { return productActualPrice; }
    public void setProductActualPrice(Double productActualPrice) { this.productActualPrice = productActualPrice;}
    
    public Set<ImageModel> getProductImages() { return productImages; }
    public void setProductImages(Set<ImageModel> productImages) { this.productImages = productImages;}

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", productName=" + productName + ", productDescription="
                + productDescription + ", productDiscountedPrice=" + productDiscountedPrice + ", productActualPrice="
                + productActualPrice + ", productImages=" + productImages + "]";
    }

    public static Product create(SqlRowSet rs) {
		Product p = new Product();
		p.setProductId(rs.getInt("product_id"));
		p.setProductName(rs.getString("product_name"));
		p.setProductDescription(rs.getString("product_description"));
		p.setProductDiscountedPrice(rs.getDouble("product_discounted_price"));
		p.setProductActualPrice(rs.getDouble("product_actual_price"));

        p.setProductImages(extractImageFromResultSet(rs));
    
		System.out.println("this is productToString:" + p.toString());
        return p;
	}

    private static Set<ImageModel> extractImageFromResultSet(SqlRowSet rs) {
        Set<ImageModel> images = new HashSet<>();
        ImageModel image = new ImageModel();
        image.setId(rs.getLong("id"));
        image.setName(rs.getString("name"));
        image.setType(rs.getString("type"));

        byte[] binaryData = new byte[rs.getMetaData().getColumnDisplaySize(1)]; // 1 is the column index
        for (int i = 0; i < binaryData.length; i++) {
            binaryData[i] = rs.getByte("picByte");
        }

        image.setPicByte(binaryData);

        images.add(image);
        return images;
    }

    

    

    // private Integer id;
    // private String name;
    // private String category;
    // private Double price;
    // private String image;
    // private Integer quantity;

    // public String getName() { return name; }
    // public void setName(String name) { this.name = name; }

    // public void setId(Integer id) { this.id = id; }
    // public Integer getId() { return id; }
    
    // public Integer getQuantity() { return quantity; }
    // public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    // public String getCategory() { return category; }
    // public void setCategory(String category) { this.category = category; }

    // public Double getPrice() { return price; }
    // public void setPrice(Double price) { this.price = price; }

    // public String getImage() { return image; }
    // public void setImage(String image) { this.image = image; }

    // public static Product create(JsonObject jo) {
    //     Product p = new Product();
    //     p.setId(jo.getInt("id"));
    //     p.setName(jo.getString("title"));
    //     p.setCategory(jo.getString("category"));
    //     p.setPrice(Double.valueOf(jo.getInt("price")));
    //     p.setImage(jo.getString("thumbnail"));
    //     p.setQuantity(0);
        
    //     return p;
    // }

    // public JsonObject toJson() {
    //     return Json.createObjectBuilder()
    //         .add("id", id)
    //         .add("name", name)
    //         .add("category", category)
    //         .add("price", price)
    //         .add("thumbnail", image)
    //         .add("quantity", quantity)
    //         .build();
    // }

    


    
}
