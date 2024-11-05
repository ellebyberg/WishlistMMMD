package org.example.wishlistmmmd.model;

import java.time.LocalDate;
import java.util.List;

public class UserProfile {
    private int userID;
    private String name;
    private String gender;
    private LocalDate birthdate;
    private String username;
    private String password;
    private List<WishList> listOfWishList;

    public UserProfile(int userID, String name, String gender, LocalDate birthdate, String username, String password) {
        this.userID = userID;
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.username = username;
        this.password = password;
    }
    public UserProfile(String name, String gender, LocalDate birthdate, String username, String password) {
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.username = username;
        this.password = password;
    }

    public void setListOfWishList(List<WishList> listOfWishList) {
        this.listOfWishList = listOfWishList;
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int newUserID) {
        this.userID = newUserID;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<WishList> getListOfWishList() {
        return listOfWishList;
    }
}
