package org.example.wishlistmmmd.model;

import java.util.Date;
import java.util.List;

public class UserProfile {
    private int userID;
    private String name;
    private Date birthdate;
    private String username;
    private String password;
    private List<WishList> listOfWishList; //BRUGER VI DENNE?

    public UserProfile(int userID, String name, Date birthdate, String username, String password) {
        this.userID = userID;
        this.name = name;
        this.birthdate = birthdate;
        this.username = username;
        this.password = password;
    }
    public UserProfile(String name, Date birthdate, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.birthdate = birthdate;
    }

    public UserProfile() {
        //Tom konstruktør til testbrug
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

    public Date getBirthdate() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Name: "+name+" Birthdate: "+birthdate+" Username: "+username+" Password: "+password;
    }
}
