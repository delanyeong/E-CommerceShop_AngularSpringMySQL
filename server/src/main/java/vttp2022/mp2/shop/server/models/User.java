package vttp2022.mp2.shop.server.models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class User {
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userPassword;

    private Set<Role> role;

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserFirstName() { return userFirstName; }
    public void setUserFirstName(String userFirstName) { this.userFirstName = userFirstName; }

    public String getUserLastName() { return userLastName; }
    public void setUserLastName(String userLastName) { this.userLastName = userLastName; }

    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    public Set<Role> getRole() { return role; }
    public void setRole(Set<Role> role) { this.role = role; }

    public static User create(SqlRowSet rs) {
		User u = new User();
		u.setUserName(rs.getString("user_name"));
		u.setUserFirstName(rs.getString("user_first_name"));
		u.setUserLastName(rs.getString("user_last_name"));
		u.setUserPassword(rs.getString("user_password"));
    u.setRole(extractRoleFromResultSet(rs));
    
		System.out.println("this is userToString:" + u.toString());
    return u;
	}

  private static Set<Role> extractRoleFromResultSet(SqlRowSet rs) {
    Set<Role> roles = new HashSet<>();
    Role role = new Role();
    role.setRoleName(rs.getString("role_name"));
    role.setRoleDescription(rs.getString("role_description"));
    roles.add(role);
    return roles;
  }

  @Override
  public String toString() {
    return "User [userName=" + userName + ", userFirstName=" + userFirstName + ", userLastName=" + userLastName
        + ", userPassword=" + userPassword + ", role=" + role + "]";
  }


    

    
}
