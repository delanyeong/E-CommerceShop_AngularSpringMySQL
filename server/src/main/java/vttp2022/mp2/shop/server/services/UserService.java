package vttp2022.mp2.shop.server.services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.mp2.shop.server.models.User;
import vttp2022.mp2.shop.server.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;

    public User registerNewUser(User user) throws SQLException {
        return userRepo.save(user);
    }

    
    

}
