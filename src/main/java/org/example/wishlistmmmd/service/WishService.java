package org.example.wishlistmmmd.service;

import org.example.wishlistmmmd.model.UserProfile;
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
    public void addUserToDB(UserProfile up) throws SQLException {
        wr.addUserToDB(up);
    }
    public boolean isUsernameAvailable(String username) throws SQLException {
        return wr.isUsernameAvailable(username);
    }
    public void resetPassword(String password, String username) throws SQLException {
        wr.resetPassword(password, username);
    }
}
