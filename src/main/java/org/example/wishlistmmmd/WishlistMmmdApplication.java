package org.example.wishlistmmmd;

import org.example.wishlistmmmd.repository.ConnectionManager;
import org.example.wishlistmmmd.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class WishlistMmmdApplication implements CommandLineRunner {

    @Autowired
    private WishRepository wr;

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(WishlistMmmdApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        wr.getUserData("cliu");
    }



}
