package vttp2022.mp2.shop.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.mp2.shop.server.models.Role;
import vttp2022.mp2.shop.server.models.User;

import static vttp2022.mp2.shop.server.repositories.Queries.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Role save(Role role) throws SQLException {
        int rowsAffected = jdbcTemplate.update(SQL_SAVE_NEW_ROLE, role.getRoleName(), role.getRoleDescription());

        if (rowsAffected == 0) {
            throw new SQLException("Creating role failed, no rows affected.");
        }

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_ROLE_BY_ROLENAME, role.getRoleName());
        if (!rs.next())
			// return Optional.empty();
            throw new SQLException("Creating role failed, no ID obtained.");
		return Role.create(rs);
    }

    // In your code, define methods to add and remove relationships between instances of User and Role. These methods should insert and delete rows in the user_role table:
    public void addUserRole(User user, Role role) {
        jdbcTemplate.update(SQL_ADD_USER_ROLE, user.getUserName(), role.getRoleName());
    }

    public void removeUserRole(User user, Role role) {
        jdbcTemplate.update(SQL_DELETE_USER_ROLE, user.getUserName(), role.getRoleName());
    }
    
    // Define a method to get all roles associated with a given user. This method should execute a query that joins the user_role and role tables and selects the appropriate rows:
    //Here, BeanPropertyRowMapper is a Spring Boot class that maps rows of a ResultSet to instances of a Java class using reflection.
    public Set<Role> getUserRoles(User user) {
        return new HashSet<>(jdbcTemplate.query(SQL_GET_ALL_USER_ROLES, new BeanPropertyRowMapper<>(Role.class), user.getUserName()));
    }

    public Optional<Role> findById(String role) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_ROLE_BY_ROLENAME, role);
        if (!rs.next())
			return Optional.empty();
		return Optional.of(Role.create(rs));
    }


}
