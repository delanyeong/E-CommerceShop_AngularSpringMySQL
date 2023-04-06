package vttp2022.mp2.shop.server.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;


import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import vttp2022.mp2.shop.server.models.ImageModel;
import vttp2022.mp2.shop.server.models.Product;
import vttp2022.mp2.shop.server.services.ProductAPIService;
import vttp2022.mp2.shop.server.services.ProductService;

@RestController
@RequestMapping
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productSvc;
    
    @PostMapping(path="/addNewProduct", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product addNewProduct(
        @RequestPart("product") Product product,
        @RequestPart("imageFile") MultipartFile[] file) throws SQLException{

        try {
            Set<ImageModel> images = uploadImage(file);
            product.setProductImages(images);
            return productSvc.addNewProduct(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //processimage
    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException{
        Set<ImageModel> imageModels = new HashSet<>();
        for (MultipartFile file : multipartFiles) {
            ImageModel imageModel = new ImageModel(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes()
            );
            imageModels.add(imageModel);
        }

        return imageModels;
    }

    // @Autowired
    // private ProductAPIService productAISvc;

    // @GetMapping (path="getProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    // @ResponseBody
    // public ResponseEntity<String> getProducts() {

    //     List<Product> products = productAISvc.getProducts();
    //     JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
    //     products.stream()
    //         .forEach(v -> {
    //             arrBuilder.add(v.toJson());
    //         });

    //     return ResponseEntity.ok(arrBuilder.build().toString());

    // }



}
