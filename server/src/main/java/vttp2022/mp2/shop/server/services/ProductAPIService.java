package vttp2022.mp2.shop.server.services;

import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.mp2.shop.server.models.Product;
import vttp2022.mp2.shop.server.repositories.ProductRepository;


@Service
public class ProductAPIService {

    @Autowired
    private ProductRepository productRepo;

    private static final String URL = "https://dummyjson.com/products/";

    // @Value("${API_KEY}")
    // private String key;

    public List<Product> getProducts() {

        String url = UriComponentsBuilder.fromUriString(URL)
                .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        String payload = resp.getBody();
        System.out.println("payload: " + payload);
        Reader strReader = new StringReader(payload);
        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject gifResult = jsonReader.readObject();
        JsonArray gifData = gifResult.getJsonArray("products");

        List<Product> gifList = new LinkedList<>();
        for (int i = 0; i < gifData.size(); i++) {
            // data[i]
            JsonObject gifDataElements = gifData.getJsonObject(i);
            gifList.add(Product.create(gifDataElements));
        }
        System.out.println(gifList.toString());
        productRepo.saveProducts(gifList);
        return gifList;

    }
}
