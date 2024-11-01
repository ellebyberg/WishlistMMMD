package org.example.wishlistmmmd.service;

import org.example.wishlistmmmd.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class WishService {
    private WishRepository wr;
    public WishService() {
        wr = new WishRepository();
    }
    public boolean validateLogin(String username, String password) throws SQLException {
        return wr.validateLogin(username, password);
    }
}
