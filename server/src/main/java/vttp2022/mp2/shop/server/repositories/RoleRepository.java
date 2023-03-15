package vttp2022.mp2.shop.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.mp2.shop.server.models.Role;

import static vttp2022.mp2.shop.server.repositories.Queries.*;

import java.sql.SQLException;
import java.util.Optional;

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
    


}
