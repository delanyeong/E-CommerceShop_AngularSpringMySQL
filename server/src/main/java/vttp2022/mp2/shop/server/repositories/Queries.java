package vttp2022.mp2.shop.server.repositories;

public class Queries {
    
    // Products (first draft)
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

    // ctd from above
    public static final String SQL_INIT_PRODUCTS = """
            INSERT INTO product (id, name, category, price, image, quantity)
            VALUES (?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE 
            name = VALUES(name), 
            category = VALUES(category),
            price = VALUES(price),
            image = VALUES(image)
        """
    ;

    // Product (second draft)
    public static final String SQL_ADD_NEW_PRODUCT = """
            INSERT INTO product (product_name, product_description, product_discounted_price, product_actual_price)
            VALUES (?, ?, ?, ?)
        """;

    public static final String SQL_FIND_PRODUCT_BY_NAME = """
            select * from product where product_name = ?
        """;

    // Product (find all)
    public static final String SQL_FIND_ALL_PRODUCTS = """
            SELECT * from product
        """;

    // Delete
    public static final String SQL_SELECT_IMAGEID = """
        SELECT image_id FROM product_images WHERE product_id = ?
        """;

    public static final String SQL_DELETEPRODUCTBYID1_PRODUCTIMAGES = """
        DELETE FROM product_images WHERE product_id = ?
        """;
        
    public static final String SQL_DELETEPRODUCTBYID2_IMAGEMODEL = """
        DELETE FROM image_model WHERE id IN
        """;
            
    public static final String SQL_DELETEPRODUCTBYID3_PRODUCT = """
        DELETE FROM product WHERE product_id = ?
        """;

    public static final String SQL_DELETE_PRODUCT = """
        DELETE FROM product WHERE product_id = ?
        """;
    
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

    public static final String SQL_FIND_USERNAME = """
            SELECT u.user_name, u.user_first_name, u.user_last_name, u.user_password, r.role_name, r.role_description
            FROM user u
            JOIN user_role ur ON u.user_name = ur.user_name
            JOIN role r ON ur.role_name = r.role_name
            WHERE u.user_name = ?
        """;

    
    // 
    public static final String SQL_UPDATE_PRODUCT = """
            UPDATE product SET product_name=?, product_description=?, product_discounted_price=?, product_actual_price=? WHERE product_id=?
        """;

    public static final String SQL_FIND_PRODUCT_BY_ID = """
            SELECT * FROM product WHERE product_id=?
        """;


    
}
