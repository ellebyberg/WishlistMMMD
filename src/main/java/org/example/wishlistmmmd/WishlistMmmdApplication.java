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
    //Ved at implementere CommandLineRunner, kan du køre kode, når
    // Spring applikationen er startet op. Metoden run bliver kaldt
    // automatisk efter, at applikationen er startet.

    @Autowired
    private WishRepository wr;
    //Denne annotation Autowired fortæller Spring, at den skal injicere en
    // instans af WishRepository i denne klasse. Spring håndterer automatisk
    // livscyklussen for beans, så vi ikke selv skal oprette dem med new.


    public static void main(String[] args) throws SQLException {
        SpringApplication.run(WishlistMmmdApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        wr.getUserData("dkim");
    }



}
