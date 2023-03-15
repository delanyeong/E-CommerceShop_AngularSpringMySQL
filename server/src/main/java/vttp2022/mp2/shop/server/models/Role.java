package vttp2022.mp2.shop.server.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Role {
    private String roleName;
    private String roleDescription;

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public String getRoleDescription() { return roleDescription; }
    public void setRoleDescription(String roleDescription) { this.roleDescription = roleDescription; }

    public static Role create(SqlRowSet rs) {
		Role r = new Role();
		r.setRoleName(rs.getString("role_name"));
		r.setRoleDescription(rs.getString("role_description"));
		return r;
	}
}
