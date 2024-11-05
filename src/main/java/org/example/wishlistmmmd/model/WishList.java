package org.example.wishlistmmmd.model;

import java.util.Date;
import java.util.List;

public class WishList {

    private int wishListID;
    private String listName;
    private Date expireDate;
    private List<Wish> wishesOnTheList;

    public WishList(String listName, Date expireDate, int wishListID) {
        this.listName = listName;
        this.expireDate = expireDate;
        this.wishListID = wishListID;
    }

    public int getID() {
        return wishListID;
    }

    public void setWishListID(int wishListID) {
        this.wishListID = wishListID;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public List<Wish> getWishesOnTheList() {
        return wishesOnTheList;
    }

    public void setWishesOnTheList(List<Wish> wishesOnTheList) {
        this.wishesOnTheList = wishesOnTheList;
    }
}
