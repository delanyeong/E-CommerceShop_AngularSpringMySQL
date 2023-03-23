package vttp2022.mp2.shop.server.repositories;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import static vttp2022.mp2.shop.server.repositories.Queries.*;

import vttp2022.mp2.shop.server.models.Role;
import vttp2022.mp2.shop.server.models.User;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public User save(User user) throws SQLException {
        int rowsAffected = jdbcTemplate.update(SQL_SAVE_NEW_USER, user.getUserName(), user.getUserFirstName(), user.getUserLastName(), user.getUserPassword());

        int rowsAffected2 = jdbcTemplate.update(SQL_SAVE_NEW_USEROLE, user.getUserName(), getRoleName(user.getRole()));

        if (rowsAffected == 0 || rowsAffected2 == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_USERNAME, user.getUserName());
        if (!rs.next())
			// return Optional.empty();
            throw new SQLException("Creating role failed, no ID obtained.");
		return User.create(rs);
    }

    public Optional<User> findById(String userName) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_USERNAME, userName);
        if (!rs.next())
			return Optional.empty();
		return Optional.of(User.create(rs));
    }

    public static String getRoleName(Set<Role> roles) {
        if (roles.size() == 1) {
            return roles.iterator().next().getRoleName();
        } else {
            throw new IllegalArgumentException("Set must contain exactly one Role");
        }
    }

    


}
