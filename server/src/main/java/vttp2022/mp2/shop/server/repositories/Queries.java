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
    public static final String SQL_SAVE_NEW_ROLE = """
            INSERT IGNORE INTO role (role_name, role_description) VALUES (?, ?)
        """;

    public static final String SQL_FIND_ROLE_BY_ROLENAME = """
            select * from role where role_name = ?
        """;
}
