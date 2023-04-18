package vttp2022.mp2.shop.server.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @PreAuthorize("hasRole('Admin')")
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

    // @GetMapping(path="/getAllProducts")
    // public List<Product> getAllProducts() {
    //     return productSvc.getAllProducts();
    // }

    // @GetMapping(path="/getAllProducts")
    // public List<Product> getAllProducts(@RequestParam(defaultValue="0") int pageNumber) {
    //     return productSvc.getAllProducts(pageNumber);
    // }

    @GetMapping(path="/getAllProducts")
    public List<Product> getAllProducts(@RequestParam(defaultValue="0") int pageNumber, @RequestParam(defaultValue = "") String searchKey) {
        List<Product> result = productSvc.getAllProducts(pageNumber, searchKey);
        System.out.println("Result size is "+ result.size());
        return result;
    }

    @GetMapping({"/getProductDetailsById/{productId}"})
    public Product getProductDetailsById(@PathVariable("productId") Integer productId) throws SQLException, IOException {
        return productSvc.getProductDetailsById(productId);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping(path="/deleteProductDetails/{productId}")
    public void deleteProductDetails(@PathVariable("productId") Integer productId) throws SQLException {
        productSvc.deleteProductDetails(productId);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping(path="/getProductDetails/{isSingleProductCheckout}/{productId}")
    public List<Product> getProductDetails(@PathVariable(name="isSingleProductCheckout") boolean isSingleProductCheckout, @PathVariable(name = "productId") Integer productId) throws SQLException {
        return productSvc.getProductDetails(isSingleProductCheckout, productId);
    }


    // NOT IN USE
    /* *** GIPHY template try only (Not in use) ***
        @Autowired
        private ProductAPIService productAISvc;

        @GetMapping (path="getProducts", produces = MediaType.APPLICATION_JSON_VALUE)
        @ResponseBody
        public ResponseEntity<String> getProducts() {

            List<Product> products = productAISvc.getProducts();
            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
            products.stream()
                .forEach(v -> {
                    arrBuilder.add(v.toJson());
                });

            return ResponseEntity.ok(arrBuilder.build().toString());
        }
    */

    




}
