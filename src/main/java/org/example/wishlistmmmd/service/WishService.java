package org.example.wishlistmmmd.service;

import jakarta.servlet.http.HttpSession;
import org.example.wishlistmmmd.model.UserProfile;
import org.example.wishlistmmmd.model.Wish;
import org.example.wishlistmmmd.model.WishList;
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

    public void createWish(int wishListID, String wishName, String description, String link) {
        wr.createWish(wishListID, wishName, description, link);
    }

    public void deleteWish(int wishID) {
        wr.deleteWish(wishID);
    }

    public String getWishListNameFromID(int wishListID) {
        return wr.getWishListNameFromID(wishListID);
    }
    public boolean doesUserOwnWishlist(int wishlistID, int userID) {
        WishList wishlist = wr.getWishListByWlIdAndUserId(wishlistID, userID);
        if (wishlist != null) {
            return true; //Det må antages, at user ejer en wishlist, hvis den ikke er null. Tjek dokumentation i Repository lag for getWishListByWlIdAndUserId() for mere information.
        } else {
            return false;
        }
    }
    public void checkExpiredListAndDelete() throws SQLException {
        wr.checkExpiredListAndDelete();
    }
    //////HJÆLPEMETODE TIL CONTROLLEREN////////

    public String redirectUserLoginAttributes(HttpSession session, int userID) {
        Integer sessionUserID = (Integer) session.getAttribute("userID");

        if (sessionUserID == null) {
            //Hvis brugeren ikke er logget ind ligger der ikke et ID gemt på session,
            // hvorfor brugeren derfor promptes til at logge ind
            return "redirect:/makemywishcometrue/loginPage";
        }
        else if(!sessionUserID.equals(userID)) {
            /*
            Brugeren prøver at tilgå en anden brugers data.
            De bliver redirected til deres egen side, hvis de er logget ind.
             */
            return "redirect:/makemywishcometrue/"+sessionUserID;
        }
        return null;
    }

}
