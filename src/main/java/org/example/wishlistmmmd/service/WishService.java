package org.example.wishlistmmmd.service;

import org.example.wishlistmmmd.model.UserProfile;
import org.example.wishlistmmmd.model.Wish;
import org.example.wishlistmmmd.model.WishList;
import org.example.wishlistmmmd.repository.WishRepository;
import org.example.wishlistmmmd.model.UserProfile;
import org.example.wishlistmmmd.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

import java.sql.SQLException;

@Service
public class WishService {

    private final WishRepository wr;

    public WishService(WishRepository wr) {
        this.wr = wr;
    }


    public boolean validateLogin(String username, String password) throws SQLException {
        return wr.validateLogin(username, password);
    }
    public int getUserIDFromDB(String username) {
        return wr.getUserIDFromDB(username);
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

    public List<WishList> showListOfWishLists(int userID) {
        return wr.showListOfWishLists(userID);
    }

    public List<Wish> showListOfWishes(int wishListID) {
        return wr.showWishesInSpecificWishList(wishListID);
    }

    public UserProfile getUserData(int userID) {
        return wr.getUserData(userID);
    }

    public void createWishList(String listName, Date expireDate, int userID) {
        wr.createWishlist(listName, expireDate, userID);
    }

    public void deleteWishList(int wishListID) {
        wr.deleteWishList(wishListID);
    }

    public void deleteWish(int wishID) {
        wr.deleteWish(wishID);
    }

    public String getWishListNameFromID(int wishListID) {
        return wr.getWishListNameFromID(wishListID);
    }

}
