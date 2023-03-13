package vttp2022.mp2.shop.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.MediaType;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import vttp2022.mp2.shop.server.models.Product;
import vttp2022.mp2.shop.server.services.ProductAPIService;

@Controller
@RequestMapping (path="/api")
public class ProductController {

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



}
