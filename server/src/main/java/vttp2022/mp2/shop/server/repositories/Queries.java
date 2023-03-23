package vttp2022.mp2.shop.server.repositories;

public class Queries {
    
    public static final String SQL_INIT_PRODUCTS_TABLE = """
            CREATE TABLE IF NOT EXISTS products (
                id INTEGER PRIMARY KEY,
                name TEXT,
                category TEXT,
                price INTEGER,
                image TEXT,
                quantity INTEGER
            )
        """
    ;

    public static final String SQL_INIT_PRODUCTS = """
            INSERT INTO products (id, name, category, price, image, quantity)
            VALUES (?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE 
            name = VALUES(name), 
            category = VALUES(category),
            price = VALUES(price),
            image = VALUES(image)
        """
    ;

    //JWT
    //Role
    public static final String SQL_SAVE_NEW_ROLE = """
            INSERT IGNORE INTO role (role_name, role_description) VALUES (?, ?)
        """;

    public static final String SQL_FIND_ROLE_BY_ROLENAME = """
            select * from role where role_name = ?
        """;

    public static final String SQL_ADD_USER_ROLE = """
            INSERT INTO user_role (user_name, role_name) VALUES (?, ?)    
        """;

    //User
    public static final String SQL_DELETE_USER_ROLE = """
            DELETE FROM user_role WHERE user_name = ? AND role_name = ?    
        """;

    public static final String SQL_GET_ALL_USER_ROLES = """
            SELECT r.* FROM user_role ur JOIN role r ON ur.role_name = r.role_name WHERE ur.user_name = ?    
        """;

    public static final String SQL_SAVE_NEW_USER = """
            INSERT INTO user (user_name, user_first_name, user_last_name, user_password) VALUES (?, ?, ?, ?)
        """;

    public static final String SQL_SAVE_NEW_USEROLE = """
            INSERT INTO user_role (user_name, role_name)
            VALUES (?, ?) 
        """;

    public static final String SQL_FIND_USER_BY_USERNAME = """
            select * from user where user_name = ?
        """;

    public static final String SQL_FIND_USER = """
            SELECT u.user_name, u.user_first_name, u.user_last_name, u.user_password, r.role_name, r.role_description
            FROM user u
            JOIN user_role ur ON u.user_name = ur.user_name
            JOIN role r ON ur.role_name = r.role_name
            WHERE u.user_name = ?
        """;

    public static final String SQL_FIND_USERNAME = """
            SELECT u.user_name, u.user_first_name, u.user_last_name, u.user_password, r.role_name, r.role_description
            FROM user u
            JOIN user_role ur ON u.user_name = ur.user_name
            JOIN role r ON ur.role_name = r.role_name
            WHERE u.user_name = ?
            """;
    
}
