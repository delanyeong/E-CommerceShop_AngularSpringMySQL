package vttp2022.mp2.shop.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vttp2022.mp2.shop.server.models.JwtRequest;
import vttp2022.mp2.shop.server.models.JwtResponse;
import vttp2022.mp2.shop.server.services.JwtService;

@RestController
@CrossOrigin
public class JwtController {
    
    @Autowired
    private JwtService jwtService;

    @PostMapping(path="/authenticate")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception{
        return jwtService.createJwtToken(jwtRequest);
    }
}
