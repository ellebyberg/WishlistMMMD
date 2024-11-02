package org.example.wishlistmmmd.repository;

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

    public void getUserData(String username) throws SQLException {
        String SQL = "SELECT userID, name FROM userprofile WHERE username=?";

        PreparedStatement ps = dbConnection.prepareStatement(SQL);
        ps.setString(1,username);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            int userID = rs.getInt("userID");
            String name = rs.getString("name");
            System.out.println("UserID: "+userID+" Name: "+name);
        }
    }

    public void checkExpiredList() {

    }


    //CRUD WISHLIST
    public void createWishlist() {

    }

    public List<WishList> showListOfWishLists(int userID) {
        List<WishList> listOfWishLists = new ArrayList<>();
        listOfWishLists.clear();

        String SQL = "SELECT wishlist.wishlistID AS listID, wishlist.listName AS listName, wishlist.expireDate FROM wishlist\n" +
                "JOIN combiuserlist ON combiuserlist.wishListID = wishlist.wishListID\n" +
                "WHERE userID =?";

        try(PreparedStatement ps = dbConnection.prepareStatement(SQL)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int listID = rs.getInt("listID");
                String listName = rs.getString("listName");
                Date expDate = rs.getDate("expireDate");
                listOfWishLists.add(new WishList(listName,expDate,listID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfWishLists;
    }

    public List<Wish> showWishesInSpecificWishList(int wishListID) {
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

    public void deleteWish(int wishID) {

        String SQL = "DELETE FROM wish WHERE wishID =?";

        try(PreparedStatement ps = dbConnection.prepareStatement(SQL)) {
            ps.setInt(1,wishID);

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

}
