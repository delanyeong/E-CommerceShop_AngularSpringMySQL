package vttp2022.mp2.shop.server.controllers;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;

import vttp2022.mp2.shop.server.models.Role;
import vttp2022.mp2.shop.server.models.User;
import vttp2022.mp2.shop.server.repositories.RoleRepository;
import vttp2022.mp2.shop.server.repositories.UserRepository;
import vttp2022.mp2.shop.server.services.UserService;

@Controller
@RequestMapping
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userSvc;

    @Autowired
    private RoleRepository roleRepo;

    // @Autowired
    // private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // @PostConstruct
    // public void initRoleAndUser() throws SQLException {

    //     Role adminRole = new Role();
    //     adminRole.setRoleName("Admin");
    //     adminRole.setRoleDescription("Admin role");
    //     roleRepo.save(adminRole);

    //     Role userRole = new Role();
    //     userRole.setRoleName("User");
    //     userRole.setRoleDescription("Default role for newly created record");
    //     roleRepo.save(userRole);

    //     User adminUser = new User();
    //     adminUser.setUserName("admin123");
    //     adminUser.setUserPassword(getEncodedPassword("admin@pass"));
    //     adminUser.setUserFirstName("admin");
    //     adminUser.setUserLastName("admin");
    //     Set<Role> adminRoles = new HashSet<>();
    //     adminRoles.add(adminRole);
    //     adminUser.setRole(adminRoles);
    //     userRepo.save(adminUser);

    //     User user = new User();
    //     user.setUserName("delan123");
    //     user.setUserPassword(getEncodedPassword("delan@123"));
    //     user.setUserFirstName("delan");
    //     user.setUserLastName("yeong");
    //     Set<Role> userRoles = new HashSet<>();
    //     userRoles.add(userRole);
    //     user.setRole(userRoles);
    //     userRepo.save(user);
    // }
    
    @PostMapping (path = "/registerNewUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User registerNewUser (@RequestBody User user) throws SQLException {
        Role role = roleRepo.findById("User").get();
        System.out.println("this is after findById: " + role.getRoleName() + " " + role.getRoleDescription());
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        System.out.println("this is roles: " + roles.toString());
        user.setRole(roles);
        System.out.println("this is user: " + user.toString());

        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return userSvc.registerNewUser(user);
    }

    @GetMapping(path = "/forAdmin")
    @PreAuthorize("hasRole('Admin')")
    @ResponseBody
    public String forAdmin() {
        return "This URL is only accessible to admin";
    }

    @GetMapping(path = "/forUser")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    @ResponseBody
    public String forUser() {
        return "This URL is only accessible to the user";
    }

}
