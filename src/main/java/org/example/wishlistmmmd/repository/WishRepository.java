package org.example.wishlistmmmd.repository;

import org.example.wishlistmmmd.model.UserProfile;
import org.example.wishlistmmmd.model.Wish;
import org.example.wishlistmmmd.model.WishList;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class WishRepository {

    private final Connection dbConnection;
    //Eftersom vi i DatabaseConfig klassen som er lavet / annoteret til en Configurations klasse
    //har oprettet en ConnectionManager-Bean, så er det Spring der administrerer klassen
    //som en singleton, og sikrer at der kun er én åben forbindelse

    public WishRepository(ConnectionManager connectionManager) {
        this.dbConnection = connectionManager.getConn();
    }

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
            ps.setDate(2, (java.sql.Date) up.getBirthdate());
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
    public void resetPassword(String password, String username) throws SQLException {
        String sql = "UPDATE userprofile SET password = ? WHERE username = ?";
        try(PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setString(1, password);
            ps.setString(2, username);
            ps.executeUpdate();
        }
    }
    public int getUserIDFromDB(String username) {
        String sql ="SELECT userid FROM userprofile WHERE username=?";
        int userID = -1;

        try(PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setString(1,username);

            try(ResultSet rs = ps.executeQuery() ) {
                if (rs.next()) {
                    userID = rs.getInt("userid");
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return userID;
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
    public void createWishlist(String listName, Date expireDate, int userID) {

        String SQL = "INSERT INTO wishlist (listName, expireDate, userID) VALUES (?,?,?)";

        try(PreparedStatement ps = dbConnection.prepareStatement(SQL)) {
            ps.setString(1,listName);
            ps.setDate(2,expireDate);
            ps.setInt(3,userID);
            int affectedRows = ps.executeUpdate();
            //Eventuelt laver jeg metoden om til at returnere int (affected Rows), så jeg kan bruge dette i Controlleren

        } catch(SQLException e) {
            e.printStackTrace();
        }

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

    public void deleteWishList(int wishListID) {
        String SQL = "DELETE FROM wishlist WHERE wishListID=?";

        try(PreparedStatement ps = dbConnection.prepareStatement(SQL)) {
            ps.setInt(1,wishListID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //CRUD WISH
    public void createWish(int wishListID, String wishName, String description, String link) {
        // Insert the wish into the 'Wish' table
        String sqlInsertWish = "INSERT INTO Wish (wishName, description, link) VALUES (?, ?, ?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(sqlInsertWish, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, wishName);
            ps.setString(2, description);
            ps.setString(3, link);
            ps.executeUpdate();

            // Get the generated wishID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int wishID = generatedKeys.getInt(1);

                    // Insert into the 'CombiWishList' table to associate the wish with the wishList
                    String sqlInsertCombi = "INSERT INTO CombiWishList (wishID, wishListID) VALUES (?, ?)";
                    try (PreparedStatement psCombi = dbConnection.prepareStatement(sqlInsertCombi)) {
                        psCombi.setInt(1, wishID);  // The ID of the wish we just created
                        psCombi.setInt(2, wishListID);  // The wishListID passed into the method
                        psCombi.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    //HJÆLPEMETODER
    public String getWishListNameFromID(int wishListID) {
        String wishListName = "";

        String SQL = "SELECT listName FROM wishlist WHERE wishListID=?";

        try(PreparedStatement ps = dbConnection.prepareStatement(SQL)) {
            ps.setInt(1,wishListID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                wishListName = rs.getString("listName");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishListName;
    }
}
