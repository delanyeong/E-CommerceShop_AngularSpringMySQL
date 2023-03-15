package vttp2022.mp2.shop.server.services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.mp2.shop.server.models.Role;
import vttp2022.mp2.shop.server.repositories.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepo;

    public Role createNewRole(Role role) throws SQLException {
        return roleRepo.save(role);
    }

}
