package org.example.wishlistmmmd;

import org.example.wishlistmmmd.model.WishList;
import org.example.wishlistmmmd.repository.WishRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class WishlistMmmdApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(WishlistMmmdApplication.class, args);
    }

}
