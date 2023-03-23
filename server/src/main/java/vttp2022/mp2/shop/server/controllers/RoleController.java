package vttp2022.mp2.shop.server.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.MediaType;

import vttp2022.mp2.shop.server.models.Role;
import vttp2022.mp2.shop.server.services.RoleService;

@Controller
@RequestMapping
@CrossOrigin
public class RoleController {
    
    @Autowired
    private RoleService roleSvc;


    @PostMapping(path="/createNewRole", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Role createNewRole(@RequestBody Role role) throws SQLException {
        return roleSvc.createNewRole(role);
    }

}
