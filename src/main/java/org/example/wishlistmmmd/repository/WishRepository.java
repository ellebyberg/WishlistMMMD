package org.example.wishlistmmmd.repository;

import org.example.wishlistmmmd.model.UserProfile;
import org.example.wishlistmmmd.model.Wish;
import org.example.wishlistmmmd.model.WishList;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class WishRepository {

    private Connection dbConnection = ConnectionManager.getInstance().getConn();

    //USER
    public void validateLogin() {

    }

    public UserProfile getUserData(int userID) {
        UserProfile up = null;

        String SQL = "SELECT name, userid, birthdate, password, username FROM userprofile WHERE userID=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(SQL)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                Date birthdate = rs.getDate("birthdate");
                String password = rs.getString("password");
                String username = rs.getString("username");

                up = new UserProfile(userID, name, birthdate, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return up;
    }

    public void checkExpiredList() {

    }


    //CRUD WISHLIST
    public void createWishlist() {

    }

    public List<WishList> showListOfWishLists(int userID) {
        List<WishList> listOfWishLists = new ArrayList<>();
        listOfWishLists.clear();

        String SQL = "SELECT wishlist.wishlistID AS listID, wishlist.listName AS listName, " +
                "wishlist.expireDate FROM wishlist WHERE userID =?";

        try (PreparedStatement ps = dbConnection.prepareStatement(SQL)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int listID = rs.getInt("listID");
                String listName = rs.getString("listName");
                Date expDate = rs.getDate("expireDate");
                listOfWishLists.add(new WishList(listName, expDate, listID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfWishLists;
    }

    public List<Wish> showWishesInSpecificWishList(int wishListID) {

        List<Wish> listOfWishes = new ArrayList<>();

        String SQL = "SELECT wish.wishname, wish.description, wish.wishid, link FROM wish \n" +
                "INNER JOIN combiwishlist ON wish.wishid = combiwishlist.wishid WHERE wishlistid = ?;";

        try (PreparedStatement ps = dbConnection.prepareStatement(SQL)) {
            ps.setInt(1, wishListID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int wishID = rs.getInt("wishid");
                String wishName = rs.getString("wishname");
                String wishDescription = rs.getString("description");
                String link = rs.getString("link");
                listOfWishes.add(new Wish(wishID, wishName, wishDescription, link));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfWishes;
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

    public void deleteWish(int wishID) {

        String SQL = "DELETE FROM wish WHERE wishID =?";

        try (PreparedStatement ps = dbConnection.prepareStatement(SQL)) {
            ps.setInt(1, wishID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
