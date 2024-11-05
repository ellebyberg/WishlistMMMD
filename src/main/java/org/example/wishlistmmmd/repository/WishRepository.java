package org.example.wishlistmmmd.repository;

import org.example.wishlistmmmd.model.UserProfile;
import org.example.wishlistmmmd.model.Wish;
import org.example.wishlistmmmd.model.WishList;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class WishRepository {

    private Connection dbConnection = ConnectionManager.getInstance().getConn();

    //USER
    public boolean validateLogin(String username, String password) throws SQLException {
        String sql = "SELECT password FROM userprofile WHERE username=?";
        /*
        Metode der laver lookup i DB for at finde eksisterende username og password. Bruger 2 try-with-resources for at
        lukke preparedstatements og resultsets automatisk.
         */
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String dbPassword = rs.getString("password");
                    return dbPassword.equals(password);
                }
            }
        }
        return false;
    }

    public void addUserToDB(UserProfile up) throws SQLException {
        String sql = "INSERT INTO userprofile(name, birthdate, username, password) VALUES(?,?,?,?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setString(1, up.getName());
//            ps.setString(2, up.getGender());
            ps.setDate(2, Date.valueOf(up.getBirthdate()));
            ps.setString(3, up.getUsername());
            ps.setString(4, up.getPassword());
            ps.executeUpdate();
        }
    }

    public boolean isUsernameAvailable(String username) throws SQLException {
        String sql = "SELECT COUNT(username) FROM userprofile WHERE username=?";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        }
        return true;
    }


    public void getUserData(String username) throws SQLException {
        String SQL = "SELECT userID, name FROM userprofile WHERE username=?";

        PreparedStatement ps = dbConnection.prepareStatement(SQL);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int userID = rs.getInt("userID");
            String name = rs.getString("name");
            System.out.println("UserID: " + userID + " Name: " + name);
        }
    }

    public void checkExpiredList() throws SQLException {
        String sql = "SELECT wishListID FROM wishlist WHERE expireDate < ?";
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);

        try(PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setDate(1, sqlDate);
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    int wishListID = rs.getInt("wishListID");
                    //TODO: Indsæt kald til deleteWishlist() når denne metode er færdig.
                }
            }
        }
    }


    //CRUD WISHLIST
    public void createWishlist() {

    }

    public List<WishList> showWishList() {
        return null;
    }

    public void updateWishList() {

    }

    public void deleteWishList() {

    }

    //CRUD WISH
    public void createWish() {

    }

    public List<Wish> showWish() {
        return null;
    }

    public void updateWish() {

    }

    public void deleteWish() {

    }

}
